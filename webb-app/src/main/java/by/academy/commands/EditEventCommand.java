package by.academy.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.logic.AdminLogic;
import by.academy.util.MessagesProperties;
import by.academy.util.PathProperties;

public class EditEventCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminLogic adminLogic = new AdminLogic();
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String EVENTS_ATTRIBUTE = "events";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String MESSAGE_ATTRIBUTE = "message";
	private final String EVENT_ANSWER_ATTRIBUTE = "editEventsList";
	private final String INPUT_LANG_ID = "inputLangId";
	private final String EVENT_ID_ATTRIBUTE = "eventId";
	private final String INPUT_DATE = "inputDate";
	private final String DATE_FORMAT = "MM/dd/yyyy HH:mm";
	private final String INPUT_START_TIME_ATTRIBUTE = "inputStartTime";
	private final String INPUT_END_TIME_ATTRIBUTE = "inputEndTime";
	private final String PERFORMANCE_ID_ATTRIBUTE = "inputPerformanceId";

	public EditEventCommand(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public String execute() throws ServletException, IOException {

		String inputLangId = request.getParameter(INPUT_LANG_ID);

		Integer langId = null;

		if (inputLangId != null) {

			langId = Integer.parseInt(inputLangId);
		}
		if (langId == null) {

			langId = 1;
		}

		Calendar eventsStartDate = new GregorianCalendar();
		Calendar eventsEndDate = new GregorianCalendar();

		String inputedDate = request.getParameter(INPUT_DATE);
		String inputedStartTime = request
				.getParameter(INPUT_START_TIME_ATTRIBUTE);
		String inputedEndTime = request.getParameter(INPUT_END_TIME_ATTRIBUTE);

		String startDate = inputedDate + " " + inputedStartTime;
		String endDate = inputedDate + " " + inputedEndTime;

		if (inputedDate != null && inputedDate.length() > 0) {

			try {
				eventsStartDate.setTime(new SimpleDateFormat(DATE_FORMAT)
						.parse(startDate));
				eventsEndDate.setTime(new SimpleDateFormat(DATE_FORMAT)
						.parse(endDate));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		} else {
			eventsStartDate.setTime(new Date());
			eventsEndDate.setTime(new Date());
		}

		Integer eventId = 0;
		String idOfEvent = request.getParameter(EVENT_ID_ATTRIBUTE);
		if (idOfEvent.length() > 0) {
			eventId = Integer.parseInt(idOfEvent);
		}
		
		Integer performanceId = 0;
		String idOfPerformance = request.getParameter(PERFORMANCE_ID_ATTRIBUTE);
		if (idOfPerformance.length() > 0) {
			performanceId = Integer.parseInt(idOfPerformance);
		}
		
		boolean flag = adminLogic.saveOrUpdateEvent(eventId, performanceId, eventsStartDate, eventsEndDate);
		
		String message = null;
		
		/*if (flag) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.REGISTER_SUCCESSFUL, "ru");
		}*/

		request.setAttribute(MENU_ITEM_ATTRIBUTE, EVENTS_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, EVENT_ANSWER_ATTRIBUTE);
		request.setAttribute(MESSAGE_ATTRIBUTE, message);

		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}
}
