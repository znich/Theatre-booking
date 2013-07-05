package by.academy.web.controller;

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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping(method = {RequestMethod.GET})
    public String showAdminPage() {
        return "adminInfo";

    }

    @RequestMapping(value = "/showPerformances", method = {RequestMethod.GET,})
    public String showPerformances(Map<String, Object> model) throws UIException, IOException {

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

    @RequestMapping(value = "/showEvents", method = {RequestMethod.GET})
    public String showEvents(Map<String, Object> model,
                             @RequestParam(value = "dateInterval", required = false) String dateInterval,
                             HttpServletRequest request) throws UIException, IOException {

        int langId = 1;

        HttpSession session = request.getSession();
        Calendar date1 = new GregorianCalendar();
        Calendar date2 = new GregorianCalendar();
        String dateFirst;
        String dateLast;
        String format = "MM/dd/yyyy";
        int sutki = 86400000;

        if (dateInterval != null) {

            session.setAttribute(RequestConstants.DATE_INTERVAL.getName(), dateInterval);
        }

        dateInterval = (String) session.getAttribute(RequestConstants.DATE_INTERVAL.getName());
        if (dateInterval != null && dateInterval.length() > 0) {
            String[] dates = dateInterval.split(" - ");
            dateFirst = dates[0];
            dateLast = dates[1];

            try {
                date1.setTime(new SimpleDateFormat(format).parse(dateFirst));
                date2.setTime(new SimpleDateFormat(format).parse(dateLast));
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new UIException("Wrong date format error", e);
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

        try {
            List<Event> eventList = siteService.getEventsInDateInterval(date1, date2, langId);

            Category category = getCategory(langId, request);

            log.info("active category" + category.getName());
            if (category != null && category.getId() != 0) {

                List<Event> sortedEventList = siteService.sortEventsByCategory(eventList, category);
                log.info("sorting events list");
                eventList = sortedEventList;
            }

            model.put(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(), eventList);
            model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
            model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());


            session.setAttribute(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(),
                    siteService.getAllCategories(langId));
        } catch (ServiceException e) {
            log.error("Cant get all categories" + e);
            throw new UIException(e);
        }

        return "editEventsList";

    }

    @RequestMapping(value = "/deleteEvent", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteEvent(Map<String, Object> model,
                              @RequestParam(value = "eventId", required = false) String idOfEvent,
                              HttpServletRequest request) throws UIException, IOException {

        try {
            int langId = 1;

            String message = null;
            if (idOfEvent == null) {
                message = MessagesProperties.createPathProperties().getProperties(
                        MessagesProperties.EVENT_DEL_ERROR, "ru");
            } else {
                Integer eventId = Integer.parseInt(idOfEvent);

                if (adminService.deleteEvent(eventId)) {
                    message = MessagesProperties.createPathProperties()
                            .getProperties(MessagesProperties.EVENT_DEL_SUCCESS, "ru");
                    log.info(message);
                }
            }
            model.put(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(), siteService.getAllEvents(langId));
            model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
            model.put(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
            model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());

            return "editEventsList";
        } catch (ServiceException e) {
            log.error("Cannot delete event");
            throw new UIException("Cannot delete event", e);
        }

    }

    @RequestMapping(value = "/deletePerformance", method = {RequestMethod.GET, RequestMethod.POST})
    public String deletPerformance(Map<String, Object> model,
                                   @RequestParam(value = "performanceId", required = false) String idOfPerformance,
                                   HttpServletRequest request) throws UIException, IOException {

        int langId = 1;
        String locale = "ru";

        String message = null;
        try {
            if (idOfPerformance == null) {
                message = MessagesProperties.createPathProperties().getProperties(
                        MessagesProperties.PERF_DEL_ERROR, locale);
            } else {
                Integer performanceId = Integer.parseInt(idOfPerformance);
                if (adminService.deletePerformance(performanceId)) {
                    message = MessagesProperties.createPathProperties()
                            .getProperties(MessagesProperties.PERF_DEL_SUCCESS, locale);
                }
            }

            request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(),
                    siteService.getAllPerformances(langId));
        } catch (ServiceException e) {
            log.info("Error while getting all performances" + e);
            throw new UIException("Error while getting all performances", e);
        }
        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), RequestConstants.PERFORMANCES_ATTRIBUTE.getName());
        model.put(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_LIST_ANSWER_ATTRIBUTE.getName());

        return "editPerformancesList";

    }

    @RequestMapping(value = "/editEvent", method = {RequestMethod.GET,
            RequestMethod.POST})
    public String editEvent(Map<String, Object> model,
                            @RequestParam(value = "inputDate", required = false) String inputedDate,
                            @RequestParam(value = "inputStartTime", required = false) String inputedStartTime,
                            @RequestParam(value = "inputEndTime", required = false) String inputedEndTime,
                            @RequestParam(value = "eventId", required = false) String idOfEvent,
                            @RequestParam(value = "performanceId", required = false) String idOfPerformance,
                            HttpServletRequest request) throws UIException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm");
        Calendar eventsStartDate = GregorianCalendar.getInstance();
        Calendar eventsEndDate = GregorianCalendar.getInstance();
        if (inputedDate != null && inputedDate.length() > 0) {
            try {
                eventsStartDate.setTime(sdf.parse(inputedDate + " " + inputedStartTime));
                eventsEndDate.setTime(sdf.parse(inputedDate + " " + inputedEndTime));
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new UIException("Wrong date format error", e);
            }
        } else {
            eventsStartDate = Calendar.getInstance();
            eventsEndDate = Calendar.getInstance();
        }

        Integer eventId = null;
        if (idOfEvent.length() > 0) {
            eventId = Integer.parseInt(idOfEvent);
        }

        Integer performanceId = 0;
        if (idOfPerformance.length() > 0) {
            performanceId = Integer.parseInt(idOfPerformance);
        }

        boolean flag;
        try {
            flag = adminService.saveOrUpdateEvent(eventId, performanceId, eventsStartDate.getTimeInMillis(),
                    eventsEndDate.getTimeInMillis());
        } catch (ServiceException e) {
            log.error("Cannot save or update event", e);
            throw new UIException("Cannot save or update event", e);
        }
        String message = null;

        if (flag) {
            message = MessagesProperties.createPathProperties().getProperties(
                    MessagesProperties.REGISTER_SUCCESSFUL, "ru");
        }

        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());
        model.put(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        return "editEventsList";

    }

    @RequestMapping(value = "/editPerformance", method = {RequestMethod.GET, RequestMethod.POST})
    public String editPerformance(Map<String, Object> model,
                                  @RequestParam(value = "inputDateInteval", required = true) String dateInterval,
                                  @RequestParam(value = "performanceId", required = true) String idOfPerformance,
                                  @RequestParam(value = "inputCategoryId", required = true) String inputCategoryId,
                                  @RequestParam(value = "inputName", required = true) String name,
                                  @RequestParam(value = "inputShortDescription", required = true) String shortDescription,
                                  @RequestParam(value = "inputDescription", required = false) String description,
                                  @RequestParam(value = "inputImage", required = true) String image,
                                  @RequestParam(value = "inputPrice", required = true) String inputPrice,
                                  @RequestParam(value = "inputLangId", required = false) String inputLangId,
                                  HttpServletRequest request) throws UIException, IOException {

        String DATE_FORMAT = "MM/dd/yyyy";

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

        if (dateInterval != null && dateInterval.length() > 0) {
            String[] dates = dateInterval.split(" - ");

            try {
                date1.setTime(new SimpleDateFormat(DATE_FORMAT).parse(dates[0]));
                date2.setTime(new SimpleDateFormat(DATE_FORMAT).parse(dates[1]));
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new UIException("Wrong date format error", e);
            }
        }

        Integer performanceId = null;
        if (idOfPerformance.length() > 0) {
            performanceId = Integer.parseInt(idOfPerformance);
        }
        Integer categoryId = Integer.parseInt(inputCategoryId);
        Set<TicketsPrice> ticketsPrices = new HashSet<TicketsPrice>();

        for (int i = 1; i <= 5; i++) {
            TicketsPrice ticketsPrice = new TicketsPrice();
            ticketsPrice.setPriceCategory(i);
            ticketsPrice.setPrice(Integer.parseInt(inputPrice + ticketsPrice.getPriceCategory()));
            ticketsPrices.add(ticketsPrice);

        }

        Category category;
        try {
            category = siteService.getCategoryById(categoryId);
        } catch (ServiceException e) {
            log.error("Error with getting Category" + e);
            throw new UIException(e);
        }

        boolean flag;
        try {
            flag = adminService.saveOrUpdatePerformance(performanceId, name,
                    shortDescription, description, date1, date2, image,
                    category, ticketsPrices, langId);
        } catch (ServiceException e) {
            log.error("Error while saving performance" + e);
            throw new UIException(e);
        }
        String message = null;

        if (flag) {
            message = MessagesProperties.createPathProperties().getProperties(
                    MessagesProperties.REGISTER_SUCCESSFUL, "ru");
        }

        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),RequestConstants.PERFORMANCES_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(),SessionConstants.EDIT_PERF_LIST_ANSWER_ATTRIBUTE.getName());
        model.put(SessionConstants.MESSAGE_ATTRIBUTE.getName(),message);

        return "editPerformancesList";

    }

    @RequestMapping(value = "/showAddEvent", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAddEvent(Map<String, Object> model,
                               HttpServletRequest request) throws UIException, IOException {

        int langId = 1;
        List<Performance> performances;
        try {
            performances = siteService.getAllPerformances(langId);
        } catch (ServiceException e) {
            log.error("Error while getting all performances" + e);
            throw new UIException(e);
        }
        String startTime = "current";
        String endTime = "current";

        model.put(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), performances);
        model.put(SessionConstants.START_TIME_ATTRIBUTE.getName(), startTime);
        model.put(SessionConstants.END_TIME_ATTRIBUTE.getName(), endTime);
        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
        model.put(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return "editEvent";

    }

    @RequestMapping(value = "/addPerformance", method = {RequestMethod.GET, RequestMethod.POST})
    public String addPerformance(Map<String, Object> model,
                                 @RequestParam(value = "inputLangId", required = false) String inputLangId,
                                 HttpServletRequest request) throws UIException, IOException {
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
            throw new UIException("Can't can't get categories", e);
        }

        List<TicketsPrice> ticketsPrices = new ArrayList<TicketsPrice>();

        for (int i = 1; i <= 5; i++) {
            TicketsPrice ticketsPrice = new TicketsPrice();
            ticketsPrice.setPriceCategory(i);
            ticketsPrices.add(ticketsPrice);
        }

        model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(),categoryList);
        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), RequestConstants.PERFORMANCES_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());
        model.put(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return "editPerformance";

    }

    @RequestMapping(value = "/showEditEvent", method = {RequestMethod.GET})
    public String showEditEvent(Map<String, Object> model,
                                @RequestParam(value = "eventId", required = true) String eId,
                                HttpServletRequest request) throws UIException, IOException {

        int langId = 1;

        Integer eventId = Integer.parseInt(eId);

        Event event;
        List<Performance> performances;
        try {

            event = siteService.getEventById(eventId, langId);
            performances = siteService.getAllPerformances(langId);
        } catch (ServiceException e) {
            log.error("Can't collect entities by siteLogic", e);
            throw new UIException("Can't collect entities by siteLogic", e);
        }

        model.put(SessionConstants.EVENT_ATTRIBUTE.getName(), event);
        model.put(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), performances);
        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
        model.put(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return "editEvent";

    }

    @RequestMapping(value = "/showEditPerformance", method = {
            RequestMethod.GET, RequestMethod.POST})
    public String showEditPerformance(Map<String, Object> model,
                                      @RequestParam(value = "inputLangId", required = false) String inputLangId,
                                      @RequestParam(value = "performanceId", required = true) String perfId,
                                      HttpServletRequest request) throws UIException, IOException {
        Integer langId = null;
        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }

        if (langId == null) {

            langId = 1;
        }

        Integer performanceId = Integer.parseInt(perfId);

        Performance performance;
        List<Category> categoryList;
        Set<TicketsPrice> ticketsPrices;
        try {
            performance = siteService.getPerformancesById(performanceId, langId);
            categoryList = siteService.getAllCategories(langId);
            ticketsPrices = performance.getTicketsPrices();
        } catch (ServiceException e) {
            log.error("Can't collect entities", e);
            throw new UIException("Can't collect entities", e);
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

        model.put(RequestConstants.PERFORMANCE_ATTRIBUTE.getName(), performance);
        model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), categoryList);
        model.put(RequestConstants.DATE_INTERVAL.getName(), dateInterval);
        model.put(SessionConstants.INPUT_LANG_ID.getName(), langId);
        model.put(SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName(), pricesList);
        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), RequestConstants.PERFORMANCES_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());

        return "editPerformance";

    }

    private Category getCategory(int langId, HttpServletRequest request) throws UIException {

        int categoryId;
        HttpSession session = request.getSession();

        if (request.getParameter(RequestConstants.CATEGORY_ID.getName()) != null) {

            categoryId = Integer.parseInt(request.getParameter(RequestConstants.CATEGORY_ID.getName()));

            session.setAttribute(RequestConstants.CATEGORY_ID.getName(), categoryId);
        }

        if (session.getAttribute(RequestConstants.CATEGORY_ID.getName()) == null) {
            categoryId = 0;
        } else {
            categoryId = (Integer) session.getAttribute(RequestConstants.CATEGORY_ID.getName());
        }

        Category category = null;
        try {
            category = siteService.getCategoryById(categoryId);
        } catch (ServiceException e) {
            log.error("Can't get category by id " + categoryId, e);
            throw new UIException("Can't get category by id " + categoryId, e);
        }

        return category;
    }
}
