package by.academy.web.controller;

import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.User;
import by.academy.domain.UserRole;
import by.academy.exception.ServiceException;
import by.academy.service.ISiteService;
import by.academy.service.IUserService;
import by.academy.utils.MessagesProperties;
import by.academy.web.exception.UIException;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */

@Controller
@RequestMapping
public class SiteController {
    private static Logger log = Logger.getLogger(SiteController.class);

    @Autowired
    private ISiteService siteService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/performances", method = {RequestMethod.GET})
        public String showPerformances(Map<String, Object> model, HttpServletRequest request,
                                       @RequestParam(value = "categoryId", required = false) String categoryId) throws UIException {
            try {
                Integer langId = (Integer)request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
                if(categoryId != null){
                    model.put(RequestConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(),
                            siteService.getPerformancesByCategory(Integer.parseInt(categoryId), langId));
                }else{
                    model.put(RequestConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteService.getAllPerformances(langId));
                }
                model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteService.getAllCategories(langId));
                return "showPerformances";
            } catch (ServiceException e) {
                log.error("ServiceException.Cannot collect performances");
                throw new UIException(e);
            }

    }

    @RequestMapping(value = "/events", method = {RequestMethod.GET, RequestMethod.POST})
    public String showEvents(Map<String, Object> model, HttpServletRequest request,
                             @RequestParam(value = "dateInterval", required = false) String dateInterval,
                             @RequestParam(value = "categoryId", required = false) String categoryId
                             ) throws UIException {
        HttpSession session = request.getSession();
        Integer langId = (Integer)session.getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());

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
        List<Event> eventList;
        try {
            eventList = siteService.getEventsInDateInterval(begin, end, langId);

        if(categoryId != null){
            session.setAttribute(RequestConstants.CATEGORY_ID.getName(), Integer.parseInt(categoryId));
        }

        Category category;
        if (session.getAttribute(RequestConstants.CATEGORY_ID.getName()) == null) {
            category = siteService.getCategoryById(0);
        } else {
            category = siteService.getCategoryById((Integer)session.getAttribute(RequestConstants.CATEGORY_ID.getName()));
        }

        if (category != null && category.getId() != 0) {

            List<Event> sortedEventList = siteService.sortEventsByCategory(eventList, category);
            eventList = sortedEventList;
        }

        model.put(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(), eventList);
        model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteService.getAllCategories(langId));
        } catch (ServiceException e) {
            log.error("Can't get events", e);
            throw new UIException("Can't get events", e);
        }
        return "showEvents";

    }

    @RequestMapping(value = "/performance", method = {RequestMethod.GET})
    public String showSinglePerformance(Map<String, Object> model, HttpServletRequest request,
                                   @RequestParam(value = "performanceId", required = false) String performanceId) throws UIException {
        try {
            Integer langId = (Integer)request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
            model.put(RequestConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteService.getPerformancesById(Integer.parseInt(performanceId), langId));
            return "showPerformance";
        } catch (ServiceException e) {
            log.error("ServiceException.Cannot get performance by id");
            throw new UIException("ServiceException.Cannot get performance by id", e);
        }

    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String showLoginForm(Model model) throws UIException {
        model.addAttribute("user", new User());
        return "showLoginForm";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(@ModelAttribute("user") User user, HttpServletRequest request) throws UIException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        String message;
        String tile;
        try {
            User validUser = userService.logination(user);
            if (validUser != null) {

                if (UserRole.ADMIN.equals(validUser.getRole())) {
                   tile = "adminInfo";
                } else {
                   tile = "showUserProfile";
                }
                session.setAttribute(SessionConstants.USER_ATTRIBUTE.getName(), user);
            } else {
                message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.ERROR_LOGIN, locale);
                tile = "showLoginForm";
            }
        } catch (ServiceException e) {
            log.error("ServiceException.Cannot login");
            throw new UIException(e);
        }
        return tile;
    }

    @RequestMapping(value = "/registration", method = {RequestMethod.GET})
    public String showRegistrationForm() throws UIException {
        return "showRegistrationForm";
    }

    @RequestMapping(value = "/registration", method = {RequestMethod.POST})
    public String registration(Map<String, Object> model, HttpServletRequest request,
                               @RequestParam(value = "firstName", required = true) String firstName,
                               @RequestParam(value = "secondName", required = true) String secondName,
                               @RequestParam(value = "inputEmail", required = true) String email,
                               @RequestParam(value = "inputPassword", required = true) String password,
                               @RequestParam(value = "inputAddress", required = true) String address,
                               @RequestParam(value = "inputPhone", required = true) String phone) throws UIException {
        String tile = "showRegisterForm";

        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        int langId = (Integer) session.getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());

        List<String> messages = new ArrayList<String>();

        userValidator.setLocale(locale);
        List<String> errors = userValidator.validateRegisterParams(firstName, secondName, email, password);
        if (!errors.isEmpty()) {
            messages.addAll(errors);
        }else {
            try {
                userService.registerUser(firstName, secondName, email, password, address, phone, langId);
            } catch (ServiceException e) {
                log.error("ServiceException.Cannot register user");
                throw new UIException(e);
            }
            tile = "showLoginForm";
            messages.add(MessagesProperties.createPathProperties().getProperties(MessagesProperties.REGISTER_SUCCESSFUL, locale));
        }

        model.put(SessionConstants.MESSAGE_ATTRIBUTE.getName(), messages);
        return tile;
    }



}
