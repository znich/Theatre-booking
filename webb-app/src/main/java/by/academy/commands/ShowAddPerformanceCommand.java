package by.academy.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.domain.Category;
import by.academy.domain.TicketsPrice;
import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

public class ShowAddPerformanceCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SiteLogic siteLogic = new SiteLogic();
	private final String INPUT_LANG_ID = "inputLangId";
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String PERFORMANCES_ATTRIBUTE = "performances";
	private final String CATEGORY_LIST_ATTRIBUTE = "categoryList";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String PERFORMANCE_ANSWER_ATTRIBUTE = "editPerformance";
	private final String TICKETS_PRICE_ATTRIBUTE = "ticketsPriceList";
	private final String LEGEND_ATTRIBUTE = "legend";
	private final String LEGEND = "Добавление представления";

	public ShowAddPerformanceCommand(HttpServletRequest request,
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
		


		
		List<Category> categoryList = siteLogic.getAllCategories(langId);
		
		List<TicketsPrice> ticketsPrices = new ArrayList<TicketsPrice>();
		
		for (int i=1;i<=7;i++){
		TicketsPrice ticketsPrice = new TicketsPrice();
		ticketsPrice.setPriceCategory(i);
		ticketsPrices.add(ticketsPrice);
		}
		
		

		
		request.setAttribute(CATEGORY_LIST_ATTRIBUTE, categoryList);
		
		session.setAttribute(TICKETS_PRICE_ATTRIBUTE, ticketsPrices);
		request.setAttribute(MENU_ITEM_ATTRIBUTE, PERFORMANCES_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, PERFORMANCE_ANSWER_ATTRIBUTE);
		request.setAttribute(LEGEND_ATTRIBUTE, LEGEND);

		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}

}
