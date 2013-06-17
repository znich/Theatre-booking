package by.academy.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.domain.Performance;
import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

public class ShowAddEventCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SiteLogic siteLogic = new SiteLogic();
	private final String INPUT_LANG_ID = "inputLangId";
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String EVENTS_ATTRIBUTE = "events";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String EDIT_EVENT_ANSWER_ATTRIBUTE = "editEvent";
	private final String LEGEND_ATTRIBUTE = "legend";
	private final String LEGEND = "Добавление события";
	private final String PERFORMANCES_LIST_ATTRIBUTE = "performancesList";
	private final String START_TIME_ATTRIBUTE = "startTime";
	private final String END_TIME_ATTRIBUTE = "endTime";
	
	public ShowAddEventCommand(HttpServletRequest request,
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
		
		List<Performance> performances = siteLogic.getAllPerformances(langId);
		String startTime = "current";
		String endTime = "current";
		
		
		request.setAttribute(PERFORMANCES_LIST_ATTRIBUTE, performances);		
		request.setAttribute(START_TIME_ATTRIBUTE, startTime);
		request.setAttribute(END_TIME_ATTRIBUTE, endTime);
		request.setAttribute(MENU_ITEM_ATTRIBUTE, EVENTS_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, EDIT_EVENT_ANSWER_ATTRIBUTE);
		request.setAttribute(LEGEND_ATTRIBUTE, LEGEND);
		
		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}

}
