package by.academy.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;
import by.academy.exception.ServiceException;
import by.academy.service.IAdminService;
import by.academy.service.ISiteService;
import by.academy.utils.MessagesProperties;
import by.academy.web.exception.UIException;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;

/**
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	private static Logger log = Logger.getLogger(AdminController.class);

	@Autowired
	private ISiteService siteService;

	@Autowired
	private IAdminService adminService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String showAdminPage(Map<String, Object> model) throws UIException {
		return "adminInfo";

	}

	@RequestMapping(value = "/showPerformances", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showPerformances(Map<String, Object> model)
			throws UIException, IOException {

		int langId = 1;

		try {
			model.put(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(),
					siteService.getAllPerformances(langId));
		} catch (ServiceException e) {
			log.error("Can't get performances", e);
			throw new UIException("Can't get performances", e);
		}
		model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName());
		model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_PERF_LIST_ANSWER_ATTRIBUTE.getName());

		return "editPerformancesList";

	}

	@RequestMapping(value = "/showEvents", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showEvents(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		int langId = 1;

		HttpSession session = request.getSession();
		Calendar date1 = new GregorianCalendar();
		Calendar date2 = new GregorianCalendar();
		String dateFirst;
		String dateLast;
		String format = "MM/dd/yyyy";
		String dateInterval = "";
		int sutki = 86400000;

		if (request.getParameter(RequestConstants.DATE_INTERVAL.getName()) != null) {

			dateInterval = request.getParameter(RequestConstants.DATE_INTERVAL
					.getName());
			log.info("dateInterval=" + dateInterval);

			session.setAttribute(RequestConstants.DATE_INTERVAL.getName(),
					dateInterval);
		}

		dateInterval = (String) session
				.getAttribute(RequestConstants.DATE_INTERVAL.getName());
		if (dateInterval != null && dateInterval.length() > 0) {
			String[] dates = dateInterval.split(" - ");
			dateFirst = dates[0];
			dateLast = dates[1];

			try {
				date1.setTime(new SimpleDateFormat(format).parse(dateFirst));
				date2.setTime(new SimpleDateFormat(format).parse(dateLast));
			} catch (ParseException e) {
				log.error("Wrong date format error", e);
				throw new ServiceException("Wrong date format error", e);
			}

		} else {
			date1.setTime(new Date());
			date2.setTime(new Date());
			date1.set(Calendar.DATE, 1);
			date2.set(Calendar.MONTH, date2.get(Calendar.MONTH) + 1);
			date2.setTimeInMillis(date2.getTimeInMillis() - sutki);
		}

		log.info("date1=" + date1.get(Calendar.DAY_OF_MONTH) + "/"
				+ date1.get(Calendar.MONTH) + "/" + date1.get(Calendar.YEAR));
		log.info("date2=" + date2.get(Calendar.DAY_OF_MONTH) + "/"
				+ date2.get(Calendar.MONTH) + "/" + date2.get(Calendar.YEAR));

		List<Event> eventList = siteService.getEventsInDateInterval(date1,
				date2, langId);

		Category category = getCategory(langId, request);

		log.info("active category" + category.getName());
		if (category != null && category.getId() != 0) {

			List<Event> sortedEventList = siteService.sortEventsByCategory(
					eventList, category);
			log.info("sorting events list");
			eventList = sortedEventList;
		}

		request.setAttribute(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(),
				eventList);
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.EVENTS_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());

		try {
			session.setAttribute(
					SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(),
					siteService.getAllCategories(langId));
		} catch (ServiceException e) {
			log.error("Cant get allcategories"+e);
			throw new ServiceException(e);
		}

		return "editEventsList";

	}

	@RequestMapping(value = "/deleteEvent", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String deleteEvent(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		int langId = 1;

		String idOfEvent = request
				.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE.getName());

		String message = null;
		if (idOfEvent == null) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.EVENT_DEL_ERROR, "ru");
		} else {
			Integer eventId = Integer.parseInt(idOfEvent);

			if (adminService.deleteEvent(eventId)) {
				message = MessagesProperties.createPathProperties()
						.getProperties(MessagesProperties.EVENT_DEL_SUCCESS,
								"ru");
				log.info(message);
			}
		}
		request.setAttribute(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(),
				siteService.getAllEvents(langId));
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.EVENTS_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(),
				message);
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());

		return "editEventsList";

	}

	@RequestMapping(value = "/deletePerformance", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String deletPerformance(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		int langId = 1;

		String idOfPerformance = request
				.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE
						.getName());
		String locale = "ru";

		String message = null;

		if (idOfPerformance == null) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.PERF_DEL_ERROR, locale);
		} else {
			Integer performanceId = Integer.parseInt(idOfPerformance);
			if (adminService.deletePerformance(performanceId)) {
				message = MessagesProperties.createPathProperties()
						.getProperties(MessagesProperties.PERF_DEL_SUCCESS,
								locale);
			}
		}
		try {
			request.setAttribute(
					SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(),
					siteService.getAllPerformances(langId));
		} catch (ServiceException e) {
			log.info("Error while getting all performances" + e);
			throw new ServiceException(e);
		}
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(),
				message);
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_PERF_LIST_ANSWER_ATTRIBUTE.getName());

		return "editPerformancesList";

	}

	@RequestMapping(value = "/editEvent", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String editEvent(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		String inputedDate = request.getParameter(SessionConstants.INPUT_DATE
				.getName());
		String inputedStartTime = request
				.getParameter(SessionConstants.INPUT_START_TIME_ATTRIBUTE
						.getName());
		String inputedEndTime = request
				.getParameter(SessionConstants.INPUT_END_TIME_ATTRIBUTE
						.getName());

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm");
		Calendar eventsStartDate = GregorianCalendar.getInstance();
		Calendar eventsEndDate = GregorianCalendar.getInstance();
		if (inputedDate != null && inputedDate.length() > 0) {
			try {
				eventsStartDate.setTime(sdf.parse(inputedDate + " "
						+ inputedStartTime));
				eventsEndDate.setTime(sdf.parse(inputedDate + " "
						+ inputedEndTime));
			} catch (ParseException e) {
				log.error("Wrong date format error", e);
				throw new ServiceException("Wrong date format error", e);
			}
		} else {
			eventsStartDate = Calendar.getInstance();
			eventsEndDate = Calendar.getInstance();
		}

		Integer eventId = null;
		String idOfEvent = request
				.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE.getName());
		if (idOfEvent.length() > 0) {
			eventId = Integer.parseInt(idOfEvent);
		}

		Integer performanceId = 0;
		String idOfPerformance = request
				.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE
						.getName());
		if (idOfPerformance.length() > 0) {
			performanceId = Integer.parseInt(idOfPerformance);
		}

		boolean flag;
		flag = adminService.saveOrUpdateEvent(eventId, performanceId,
				eventsStartDate.getTimeInMillis(),
				eventsEndDate.getTimeInMillis());
		String message = null;

		if (flag) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.REGISTER_SUCCESSFUL, "ru");
		}

		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.EVENTS_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(),
				message);

		return "editEventsList";

	}

	@RequestMapping(value = "/editPerformance", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String editPerformance(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		String DATE_FORMAT = "MM/dd/yyyy";

		String inputLangId = request
				.getParameter(SessionConstants.INPUT_LANG_ID.getName());
		log.info("input lang id=" + inputLangId);
		Integer langId = null;

		if (inputLangId != null && inputLangId.length() > 0) {

			langId = Integer.parseInt(inputLangId);
		}
		if (langId == null) {

			langId = 1;
		}

		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();

		String dateInterval = request
				.getParameter(SessionConstants.INPUT_DATE_INTERVAL.getName());

		if (dateInterval != null && dateInterval.length() > 0) {
			String[] dates = dateInterval.split(" - ");

			try {
				date1.setTime(new SimpleDateFormat(DATE_FORMAT).parse(dates[0]));
				date2.setTime(new SimpleDateFormat(DATE_FORMAT).parse(dates[1]));
			} catch (ParseException e) {
				log.error("Wrong date format error", e);
				throw new ServiceException("Wrong date format error", e);
			}
		}

		Integer performanceId = null;
		String idOfPerformance = request
				.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE
						.getName());
		if (idOfPerformance.length() > 0) {
			performanceId = Integer.parseInt(idOfPerformance);
		}
		Integer categoryId = Integer.parseInt(request
				.getParameter(SessionConstants.INPUT_CATEGORY_ATTRIBUTE
						.getName()));
		String name = request
				.getParameter(SessionConstants.INPUT_NAME_ATTRIBUTE.getName());
		String shortDescription = request
				.getParameter(SessionConstants.INPUT_SHORTDESCRIPTION_ATTRIBUTE
						.getName());
		String description = request
				.getParameter(SessionConstants.INPUT_DESCRIPTION_ATTRIBUTE
						.getName());
		String image = request
				.getParameter(SessionConstants.INPUT_IMAGE_ATTRIBUTE.getName());

		Set<TicketsPrice> ticketsPrices = new HashSet<TicketsPrice>();

		for (int i = 1; i <= 5; i++) {
			TicketsPrice ticketsPrice = new TicketsPrice();
			ticketsPrice.setPriceCategory(i);
			ticketsPrice
					.setPrice(Integer.parseInt(request
							.getParameter(SessionConstants.INPUT_TICKETS_PRICE_ATTRIBUTE
									.getName()
									+ ticketsPrice.getPriceCategory())));
			ticketsPrices.add(ticketsPrice);

		}

		Category category;
		try {
			category = siteService.getCategoryById(categoryId);
		} catch (ServiceException e) {
			log.error("Error with getting Category" + e);
			throw new ServiceException(e);
		}

		boolean flag;
		try {
			flag = adminService.saveOrUpdatePerformance(performanceId, name,
					shortDescription, description, date1, date2, image,
					category, ticketsPrices, langId);
		} catch (ServiceException e) {
			log.error("Error while saving performance" + e);
			throw new ServiceException(e);
		}
		String message = null;

		if (flag) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.REGISTER_SUCCESSFUL, "ru");
		}

		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_PERF_LIST_ANSWER_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(),
				message);

		return "editPerformancesList";

	}

	@RequestMapping(value = "/showAddEvent", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showAddEvent(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		int langId = 1;
		List<Performance> performances;
		try {
			performances = siteService.getAllPerformances(langId);
		} catch (ServiceException e) {
			log.error("Error while getting all performances" + e);
			throw new ServiceException(e);
		}
		String startTime = "current";
		String endTime = "current";

		request.setAttribute(
				SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(),
				performances);
		request.setAttribute(SessionConstants.START_TIME_ATTRIBUTE.getName(),
				startTime);
		request.setAttribute(SessionConstants.END_TIME_ATTRIBUTE.getName(),
				endTime);
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.EVENTS_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(),
				SessionConstants.LEGEND.getName());

		return "editEvent";

	}

	@RequestMapping(value = "/addPerformance", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addPerformance(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		String inputLangId = request
				.getParameter(SessionConstants.INPUT_LANG_ID.getName());
		Integer langId = null;
		if (inputLangId != null) {

			langId = Integer.parseInt(inputLangId);
		}

		if (langId == null) {

			langId = 1;
		}

		List<Category> categoryList;
		try {
			categoryList = siteService.getAllCategories(langId);
		} catch (ServiceException e) {
			log.error("Can't can't get categories", e);
			throw new ServiceException("Can't can't get categories", e);
		}

		List<TicketsPrice> ticketsPrices = new ArrayList<TicketsPrice>();

		for (int i = 1; i <= 5; i++) {
			TicketsPrice ticketsPrice = new TicketsPrice();
			ticketsPrice.setPriceCategory(i);
			ticketsPrices.add(ticketsPrice);
		}

		request.setAttribute(
				SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(),
				categoryList);

		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(),
				SessionConstants.LEGEND.getName());

		return "editPerformance";

	}

	@RequestMapping(value = "/showEditEvent", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showEditEvent(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		int langId = 1;

		Integer eventId = Integer.parseInt(request
				.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE.getName()));

		Event event;
		List<Performance> performances;
		try {

			event = siteService.getEventById(eventId, langId);
			performances = siteService.getAllPerformances(langId);
		} catch (ServiceException e) {
			log.error("Can't collect entities by siteLogic", e);
			throw new ServiceException("Can't collect entities by siteLogic", e);
		}

		request.setAttribute(SessionConstants.EVENT_ATTRIBUTE.getName(), event);
		request.setAttribute(
				SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(),
				performances);
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.EVENTS_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(),
				SessionConstants.LEGEND.getName());

		return "editEvent";

	}

	@RequestMapping(value = "/showEditPerformance", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String showEditPerformance(Map<String, Object> model,
			HttpServletRequest request) throws UIException, IOException,
			ServiceException {

		String inputLangId = request
				.getParameter(SessionConstants.INPUT_LANG_ID.getName());
		Integer langId = null;
		if (inputLangId != null) {

			langId = Integer.parseInt(inputLangId);
		}

		if (langId == null) {

			langId = 1;
		}

		Integer performanceId = Integer.parseInt(request
				.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE
						.getName()));

		Performance performance;
		List<Category> categoryList;
		Set<TicketsPrice> ticketsPrices;
		try {
			performance = siteService
					.getPerformancesById(performanceId, langId);
			categoryList = siteService.getAllCategories(langId);
			ticketsPrices = performance.getTicketsPrices();
		} catch (ServiceException e) {
			log.error("Can't collect entities", e);
			throw new ServiceException("Can't collect entities", e);
		}

		List<TicketsPrice> pricesList = new ArrayList<>();
		pricesList.addAll(ticketsPrices);
		Collections.sort(pricesList);

		Calendar cal = GregorianCalendar.getInstance();
		StringBuilder dateIntervalBulder = new StringBuilder();
		cal = performance.getStartDate();
		dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/"
				+ cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
		dateIntervalBulder.append(" - ");
		cal = performance.getEndDate();
		dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/"
				+ cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
		String dateInterval = dateIntervalBulder.toString();

		request.setAttribute(SessionConstants.PERFORMANCE_ATTRIBUTE.getName(),
				performance);
		request.setAttribute(
				SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(),
				categoryList);
		request.setAttribute(RequestConstants.DATE_INTERVAL.getName(),
				dateInterval);
		request.setAttribute(SessionConstants.INPUT_LANG_ID.getName(), langId);
		request.setAttribute(
				SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName(), pricesList);
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());

		return "editPerformance";

	}

	private Category getCategory(int langId, HttpServletRequest request)
			throws ServiceException {

		int categoryId;
		HttpSession session = request.getSession();

		if (request.getParameter(RequestConstants.CATEGORY_ID.getName()) != null) {

			categoryId = Integer.parseInt(request
					.getParameter(RequestConstants.CATEGORY_ID.getName()));

			session.setAttribute(RequestConstants.CATEGORY_ID.getName(),
					categoryId);
		}

		if (session.getAttribute(RequestConstants.CATEGORY_ID.getName()) == null) {
			categoryId = 0;
		} else {
			categoryId = (Integer) session
					.getAttribute(RequestConstants.CATEGORY_ID.getName());
		}

		Category category = null;
		try {
			category = siteService.getCategoryById(categoryId);
		} catch (ServiceException e) {
			log.error("Can't get category by id " + categoryId, e);
			throw new ServiceException(
					"Can't get category by id " + categoryId, e);
		}

		return category;
	}
}
