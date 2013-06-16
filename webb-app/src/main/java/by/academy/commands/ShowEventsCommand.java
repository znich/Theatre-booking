package by.academy.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

public class ShowEventsCommand implements ICommand {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session = null;
	private SiteLogic siteLogic = new SiteLogic();	
	private final String EVENTS_LIST_ATTRIBUTE = "eventList";
	private final String LOCALE_ID_ATTRIBUTE = "langId";
	private final String CATEGORY_ID = "categoryId";
	private final String DATE_INTERVAL = "dateInteval";
	private final String CATEGORIES_LIST_ATTRIBUTE = "categories";

	public ShowEventsCommand(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public String execute() throws ServletException, IOException {

		int langId = (Integer) request.getSession().getAttribute(
				LOCALE_ID_ATTRIBUTE);

		session = request.getSession();

		Calendar date1 = new GregorianCalendar();
		Calendar date2 = new GregorianCalendar();
		String dateFirst;
		String dateLast;
		String format = "MM/dd/yyyy";
		String dateInterval = "";
		int sutki = 86400000;

		if (request.getParameter(DATE_INTERVAL) != null) {

			dateInterval = request.getParameter(DATE_INTERVAL);

			session.setAttribute(DATE_INTERVAL, dateInterval);
		}

		dateInterval = (String) session.getAttribute(DATE_INTERVAL);
		if (dateInterval != null && dateInterval.length() > 0) {
			String[] dates = dateInterval.split(" - ");
			dateFirst = dates[0];
			dateLast = dates[1];

			try {
				date1.setTime(new SimpleDateFormat(format).parse(dateFirst));
				date2.setTime(new SimpleDateFormat(format).parse(dateLast));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			date1.setTime(new Date());
			date2.setTime(new Date());
			date1.set(Calendar.DATE, 1);
			date2.set(Calendar.MONTH, date2.get(Calendar.MONTH) + 1);
			date2.setTimeInMillis(date2.getTimeInMillis() - sutki);
		}

		List<Event> eventList = siteLogic.getEventsInDateInterval(date1, date2,
				langId);
		Category category = getCategory(langId);

		if (category != null && category.getId() != 0) {

			List<Event> sortedEventList = siteLogic.sortEventsByCategory(
					eventList, category);
			eventList = sortedEventList;
		}

		request.setAttribute(EVENTS_LIST_ATTRIBUTE, eventList);

		session.setAttribute(CATEGORIES_LIST_ATTRIBUTE,
				siteLogic.getAllCategories(langId));

		return PathProperties.createPathProperties().getProperty(
				PathProperties.EVENTS_LIST_PAGE);

	}

	private Category getCategory(int langId) {

		int categoryId;
		if (session == null) {
			session = request.getSession();
		}

		if (request.getParameter(CATEGORY_ID) != null) {

			categoryId = Integer.parseInt(request.getParameter(CATEGORY_ID));

			session.setAttribute(CATEGORY_ID, categoryId);
		}

		if (session.getAttribute(CATEGORY_ID) == null) {
			categoryId = 0;
		} else {
			categoryId = (Integer) session.getAttribute(CATEGORY_ID);
		}

		Category category = siteLogic.getCategoryById(categoryId, langId);

		return category;
	}
}
