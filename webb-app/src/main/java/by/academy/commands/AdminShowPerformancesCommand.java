package by.academy.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

public class AdminShowPerformancesCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SiteLogic siteLogic = new SiteLogic();
	private HttpSession session = null;
	private final String LOCALE_ID_ATTRIBUTE = "langId";
	private final String PERFORMANCE_LIST_ATTRIBUTE = "performanceList";
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String PERFORMANCES_ATTRIBUTE = "performances";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String PERFORMANCE_ANSWER_ATTRIBUTE = "editPerformanceList";

	public AdminShowPerformancesCommand(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
         //
	@Override
	public String execute() throws ServletException, IOException {

		session = request.getSession();

		int langId = (Integer) session.getAttribute(LOCALE_ID_ATTRIBUTE);

		request.setAttribute(PERFORMANCE_LIST_ATTRIBUTE,
				siteLogic.getAllPerformances(langId));
		request.setAttribute(MENU_ITEM_ATTRIBUTE, PERFORMANCES_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, PERFORMANCE_ANSWER_ATTRIBUTE);

		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}

}
