package by.academy.commands;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.domain.Category;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;
import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

public class ShowEditPerformanceCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SiteLogic siteLogic = new SiteLogic();
	private final String INPUT_LANG_ID = "inputLangId";
	private final String PERFORMANCE_ID_ATTRIBUTE = "performanceId";
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String PERFORMANCE_ATTRIBUTE = "performance";
	private final String PERFORMANCES_ATTRIBUTE = "performances";
	private final String CATEGORY_LIST_ATTRIBUTE = "categoryList";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String PERFORMANCE_ANSWER_ATTRIBUTE = "editPerformance";
	private final String DATE_INTERVAL = "dateInterval";
	private final String TICKETS_PRICE_ATTRIBUTE = "ticketsPriceList";
	private final String LEGEND_ATTRIBUTE = "legend";
	private final String LEGEND = "Редактирование представления";

	public ShowEditPerformanceCommand(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public String execute() throws ServletException, IOException {

		HttpSession session = request.getSession();
		String inputLangId = request.getParameter(INPUT_LANG_ID);
		Integer langId = null;
		if (inputLangId != null) {

			langId = Integer.parseInt(inputLangId);
		}

		if (langId == null) {

			langId = 1;
		}

		Integer performanceId = Integer.parseInt(request
				.getParameter(PERFORMANCE_ID_ATTRIBUTE));

		Performance performance = siteLogic.getPerformancesById(performanceId,
				langId);
		List<Category> categoryList = siteLogic.getAllCategories(langId);
		List<TicketsPrice> ticketsPrices = siteLogic
				.getTicketsPriceByPerformance(performance);

		Calendar startDate = performance.getStartDate();
		Calendar endDate = performance.getEndDate();
		StringBuilder dateIntervalBulder = new StringBuilder();
		dateIntervalBulder.append((startDate.get(Calendar.MONTH) + 1) + "/"
				+ startDate.get(Calendar.DATE) + "/"
				+ startDate.get(Calendar.YEAR));
		dateIntervalBulder.append(" - ");
		dateIntervalBulder
				.append((endDate.get(Calendar.MONTH) + 1) + "/"
						+ endDate.get(Calendar.DATE) + "/"
						+ endDate.get(Calendar.YEAR));
		String dateInterval = dateIntervalBulder.toString();

		request.setAttribute(PERFORMANCE_ATTRIBUTE, performance);
		request.setAttribute(CATEGORY_LIST_ATTRIBUTE, categoryList);
		request.setAttribute(DATE_INTERVAL, dateInterval);
		session.setAttribute(TICKETS_PRICE_ATTRIBUTE, ticketsPrices);
		request.setAttribute(MENU_ITEM_ATTRIBUTE, PERFORMANCES_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, PERFORMANCE_ANSWER_ATTRIBUTE);
		request.setAttribute(LEGEND_ATTRIBUTE, LEGEND);

		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}
	
}
