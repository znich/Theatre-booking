package by.academy.web.commands;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/19/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShowEditEventCommand implements ICommand {

    private static Log log = LogFactory.getLog(ShowEditEventCommand.class);
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ShowEditEventCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {

        SiteLogic siteLogic = null;
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }
        String inputLangId = request.getParameter(SessionConstants.INPUT_LANG_ID.getName());
        Integer langId = null;
        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }

        if (langId == null) {

            langId = 1;
        }

        Integer eventId = Integer.parseInt(request.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE.getName()));

        Event event = null;
        try {
            event = siteLogic.getEventById(eventId, langId);
        } catch (ServiceException e) {
            log.error("Can't get event by id", e);
            throw new ServiceException("Can't get event by id", e);
        }

        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(event.getStartTime());

        Calendar endDate = Calendar.getInstance();
        startDate.setTimeInMillis(event.getEndTime());

        String eventsDate = (startDate.get(Calendar.MONTH) + 1) + "/"
                + startDate.get(Calendar.DATE) + "/"
                + startDate.get(Calendar.YEAR);
        String startTime = startDate.get(Calendar.HOUR_OF_DAY) + ":"
                + startDate.get(Calendar.MINUTE);
        String endTime = endDate.get(Calendar.HOUR_OF_DAY) + ":"
                + endDate.get(Calendar.MINUTE);

        Set<Performance> performances = null;
        try {
            performances = siteLogic.getAllPerformances(langId);
        } catch (ServiceException e) {
            log.error("Can't collect performances", e);
            throw new ServiceException("Can't collect performances", e);
        }

        request.setAttribute(SessionConstants.EVENT_ATTRIBUTE.getName(), event);
        request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), performances);
        request.setAttribute(SessionConstants.EVENT_DATE.getName(), eventsDate);
        request.setAttribute(SessionConstants.START_TIME_ATTRIBUTE.getName(), startTime);
        request.setAttribute(SessionConstants.END_TIME_ATTRIBUTE.getName(), endTime);
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return null;
    }

}
