package by.academy.web.commands;

/**
 */
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShowEditEventCommand implements ICommand {

    private static Log log = LogFactory.getLog(ShowEditEventCommand.class);
    private HttpServletRequest request;

    public ShowEditEventCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {

        String inputLangId = request.getParameter(SessionConstants.INPUT_LANG_ID.getName());
        Integer langId = null;
        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }

        if (langId == null) {

            langId = 1;
        }

        Integer eventId = Integer.parseInt(request.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE.getName()));

        Event event;
        Set<Performance> performances;
        try {
            SiteLogic siteLogic = new SiteLogic();
            event = siteLogic.getEventById(eventId, langId);
            performances = siteLogic.getAllPerformances(langId);
        } catch (ServiceException e) {
            log.error("Can't collect entities by siteLogic", e);
            throw new ServiceException("Can't collect entities by siteLogic", e);
        }

/*        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(event.getStartTime());

        Calendar endDate = Calendar.getInstance();
        startDate.setTimeInMillis(event.getEndTime());

        String eventsDate = (startDate.get(Calendar.MONTH) + 1) + "/"
                + startDate.get(Calendar.DATE) + "/"
                + startDate.get(Calendar.YEAR);
        String startTime = startDate.get(Calendar.HOUR_OF_DAY) + ":"
                + startDate.get(Calendar.MINUTE);
        String endTime = endDate.get(Calendar.HOUR_OF_DAY) + ":"
                + endDate.get(Calendar.MINUTE);*/

        request.setAttribute(SessionConstants.EVENT_ATTRIBUTE.getName(), event);
        request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), performances);
/*        request.setAttribute(SessionConstants.EVENT_DATE.getName(), eventsDate);
        request.setAttribute(SessionConstants.START_TIME_ATTRIBUTE.getName(), startTime);
        request.setAttribute(SessionConstants.END_TIME_ATTRIBUTE.getName(), endTime);*/
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return PathProperties.createPathProperties().getProperty(PathProperties.ADMIN_PAGE);
    }

}
