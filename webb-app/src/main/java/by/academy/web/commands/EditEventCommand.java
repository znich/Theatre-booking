package by.academy.web.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.exception.ServiceException;
import by.academy.logic.AdminLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EditEventCommand implements ICommand {

    private static Log log = LogFactory.getLog(EditEventCommand.class);
    private final String DATE_FORMAT = "MM/dd/yyyy HH:mm";
    private HttpServletRequest request;
    private AdminLogic adminLogic;

    public EditEventCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        try {
            adminLogic = new AdminLogic();
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

        String inputedDate = request.getParameter(SessionConstants.INPUT_DATE.getName());
        String inputedStartTime = request.getParameter(SessionConstants.INPUT_START_TIME_ATTRIBUTE.getName());
        String inputedEndTime = request.getParameter(SessionConstants.INPUT_END_TIME_ATTRIBUTE.getName());

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        long eventsStartDate;
        long eventsEndDate;
        if (inputedDate != null && inputedDate.length() > 0) {

            try {
                eventsStartDate = sdf.parse(inputedDate + " " + inputedStartTime).getTime();
                eventsEndDate = sdf.parse( inputedDate + " " + inputedEndTime).getTime();
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new ServiceException("Wrong date format error", e);
            }
        } else {
            eventsStartDate = Calendar.getInstance().getTimeInMillis();
            eventsEndDate = Calendar.getInstance().getTimeInMillis();
        }

        Integer eventId = null;
        String idOfEvent = request.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE.getName());
        if (idOfEvent.length() > 0) {
            eventId = Integer.parseInt(idOfEvent);
        }

        Integer performanceId = 0;
        String idOfPerformance = request.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName());
        if (idOfPerformance.length() > 0) {
            performanceId = Integer.parseInt(idOfPerformance);
        }

        boolean flag = adminLogic.saveOrUpdateEvent(eventId, performanceId, eventsStartDate, eventsEndDate);

        String message = null;

		/*if (flag) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.REGISTER_SUCCESSFUL, "ru");
		}*/

        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);

        return PathProperties.createPathProperties().getProperty(
                PathProperties.ADMIN_PAGE);
    }
}