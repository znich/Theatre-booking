package by.academy.web.controller;

import by.academy.domain.*;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), categoryList);
        model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), RequestConstants.PERFORMANCES_ATTRIBUTE.getName());
        model.put(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());
        model.put(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return "editPerformance";

    }

    @RequestMapping(value = "/editPerformance/{id}", method = {RequestMethod.GET})
    public String showEditPerformance(Model model, @RequestParam(value = "inputLangId", required = false) String inputLangId,
                                      @PathVariable("id") Integer id) throws UIException, IOException {

        try {

            Integer langId = null;
            if (inputLangId != null) {

                langId = Integer.parseInt(inputLangId);
            }

            if (langId == null) {

                langId = 1;
            }

            Performance performance = siteService.getPerformancesById(id, langId);
            List<TicketsPrice> pricesList = new ArrayList<>(performance.getTicketsPrices());
            Collections.sort(pricesList);

            Calendar cal;
            StringBuilder dateIntervalBulder = new StringBuilder();
            cal = performance.getStartDate();
            dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/"
                    + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
            dateIntervalBulder.append(" - ");
            cal = performance.getEndDate();
            dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/"
                    + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
            String dateInterval = dateIntervalBulder.toString();

            model.addAttribute("performance", performance);
            model.addAttribute("dateInterval", dateInterval);
            model.addAttribute(SessionConstants.INPUT_LANG_ID.getName(), langId);
            model.addAttribute("categories", siteService.getAllCategories(langId));
            model.addAttribute(SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName(), pricesList);
            model.addAttribute("legend", "Редактирование спектакля");
        } catch (ServiceException e) {
            log.error("ServiceException.Cannot get performance by id");
            throw new UIException("ServiceException.Cannot get performance by id", e);
        }
        return "editPerformance";
    }

    @RequestMapping(value = "/editPerformance/{id}", method = {RequestMethod.POST})
    public String editPerformance(Map<String, Object> model,
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

        return "redirect:/admin/showPerformances";

    }

    @RequestMapping(value = "/showEvents", method = {RequestMethod.GET})
    public String showEvents(Map<String, Object> model,
                             @RequestParam(value = "dateInterval", required = false) String dateInterval,
                             HttpServletRequest request) throws UIException, IOException {

        int langId = 1;

        HttpSession session = request.getSession();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        if (dateInterval != null) {
            session.setAttribute(RequestConstants.DATE_INTERVAL.getName(), dateInterval);
        }

        dateInterval = (String) session.getAttribute(RequestConstants.DATE_INTERVAL.getName());

        if (dateInterval != null && dateInterval.length() > 0) {
            String[] dates = dateInterval.split(" - ");

            try {
                begin.setTime(sdf.parse(dates[0]));
                end.setTime(sdf.parse(dates[1]));
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new UIException("Wrong date format error", e);
            }

        } else {
            begin.add(Calendar.DAY_OF_YEAR, -14);
            end.add(Calendar.DAY_OF_YEAR, 14);
        }

        log.info("date1=" + begin.get(Calendar.DAY_OF_MONTH) + "/"
                + begin.get(Calendar.MONTH) + "/" + begin.get(Calendar.YEAR));
        log.info("date2=" + end.get(Calendar.DAY_OF_MONTH) + "/"
                + end.get(Calendar.MONTH) + "/" + end.get(Calendar.YEAR));

        try {
            List<Event> eventList = siteService.getEventsInDateInterval(begin, end, langId);

            Category category = getCategory(langId, request);

            if (category != null && category.getId() != 0) {

                List<Event> sortedEventList = siteService.sortEventsByCategory(eventList, category);
                log.info("sorting events list");
                eventList = sortedEventList;
            }

            model.put(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(), eventList);
            model.put(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
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
            return "editEventsList";
        } catch (ServiceException e) {
            log.error("Cannot delete event");
            throw new UIException("Cannot delete event", e);
        }

    }

    @RequestMapping(value = "/deletePerformance", method = {RequestMethod.GET, RequestMethod.POST})
    public String deletPerformance(Map<String, Object> model,
                                   @RequestParam(value = "performanceId", required = true) String idOfPerformance,
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

        return "redirect:/admin/showPerformances";

    }

    @RequestMapping(value = "/addEvent", method = {RequestMethod.GET})
    public String addEvent(Model model) throws UIException, IOException {
        try {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            model.addAttribute("event", new Event(currentTime, currentTime));
            model.addAttribute("performanceList", siteService.getAllPerformances(1));
            model.addAttribute("legend", "Добавление события");
            return "editEvent";
        }catch (ServiceException e){
            log.info("Service Exception. Cannot collect entities for 'showEditEvent' page");
            throw new UIException("Cannot collect entities for 'showEditEvent' page", e);
        }
    }

    @RequestMapping(value = "/editEvent/{id}", method = {RequestMethod.GET})
    public String showEditEvent(Model model,
                                @PathVariable("id") Integer id) throws UIException, IOException {
        try {
            model.addAttribute("event", siteService.getEventById(id, 1));
            model.addAttribute("performanceList", siteService.getAllPerformances(1));
            model.addAttribute("legend", "Редактирование события");

            return "editEvent";
        }catch (ServiceException e){
            log.info("Service Exception. Cannot collect entities for 'showEditEvent' page");
            throw new UIException("Cannot collect entities for 'showEditEvent' page", e);
        }
    }

    @RequestMapping(value = "/editEvent/{id}", method = {RequestMethod.POST})
    public String editEvent(@ModelAttribute("event") Event event,
                            @RequestParam(value = "inputDate", required = false) String inputedDate,
                            @RequestParam(value = "inputStartTime", required = false) String inputedStartTime,
                            @RequestParam(value = "inputEndTime", required = false) String inputedEndTime,
                            @RequestParam(value = "performanceId", required = false) String idOfPerformance) throws UIException, IOException {

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

        Integer performanceId = 0;
        if (idOfPerformance.length() > 0) {
            performanceId = Integer.parseInt(idOfPerformance);
        }

        boolean flag;
        try {
            flag = adminService.saveOrUpdateEvent(event.getId(), performanceId, eventsStartDate.getTimeInMillis(),
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

        return "redirect:/admin/showEvents";

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
