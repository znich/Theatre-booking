package by.academy.commands;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.logic.SiteLogic;

public class ShowEditEventCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SiteLogic siteLogic = new SiteLogic();
	private final String INPUT_LANG_ID = "inputLangId";
	private final String EVENT_ID_ATTRIBUTE = "eventId";
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String EVENT_ATTRIBUTE = "event";
	private final String EVENTS_ATTRIBUTE = "events";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String EDIT_EVENT_ANSWER_ATTRIBUTE = "editEvent";
	private final String EVENT_DATE = "eventsDate";
	private final String LEGEND_ATTRIBUTE = "legend";
	private final String LEGEND = "Редактирование события";
	private final String PERFORMANCES_LIST_ATTRIBUTE = "performancesList";
	private final String START_TIME_ATTRIBUTE = "startTime";
	private final String END_TIME_ATTRIBUTE = "endTime";

	public ShowEditEventCommand(HttpServletRequest request,
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

		Integer eventId = Integer.parseInt(request
				.getParameter(EVENT_ID_ATTRIBUTE));

		Event event = siteLogic.getEventById(eventId, langId);

		Calendar startDate = event.getStartTime();
		Calendar endDate = event.getEndTime();

		String eventsDate = (startDate.get(Calendar.MONTH) + 1) + "/"
				+ startDate.get(Calendar.DATE) + "/"
				+ startDate.get(Calendar.YEAR);
		String startTime = startDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ startDate.get(Calendar.MINUTE);
		String endTime = endDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ endDate.get(Calendar.MINUTE);

		List<Performance> performances = siteLogic.getAllPerformances(langId);

		request.setAttribute(EVENT_ATTRIBUTE, event);
		request.setAttribute(PERFORMANCES_LIST_ATTRIBUTE, performances);
		request.setAttribute(EVENT_DATE, eventsDate);
		request.setAttribute(START_TIME_ATTRIBUTE, startTime);
		request.setAttribute(END_TIME_ATTRIBUTE, endTime);
		request.setAttribute(MENU_ITEM_ATTRIBUTE, EVENTS_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, EDIT_EVENT_ANSWER_ATTRIBUTE);
		request.setAttribute(LEGEND_ATTRIBUTE, LEGEND);

		return null;
	}

}
