package by.academy.web.commands;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/19/13
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdminShowEventsCommand implements ICommand {

    private static Log log = LogFactory.getLog(AdminShowPerformancesCommand.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SiteLogic siteLogic;
    private HttpSession session;


    public AdminShowEventsCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }

        int langId = (Integer) request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());

        session = request.getSession();

        Calendar date1 = new GregorianCalendar();
        Calendar date2 = new GregorianCalendar();
        String dateFirst;
        String dateLast;
        String format = "MM/dd/yyyy";
        String dateInterval = "";
        int sutki = 86400000;

        if (request.getParameter(RequestConstants.DATE_INTERVAL.getName()) != null) {

            dateInterval = request.getParameter(RequestConstants.DATE_INTERVAL.getName());

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
                throw new ServiceException("Wrong date format error", e);
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

            List<Event> sortedEventList = siteLogic.sortEventsByCategory(eventList, category);
            eventList = sortedEventList;
        }

        request.setAttribute(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(), eventList);
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());

        session.setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteLogic.getAllCategories(langId));

        return PathProperties.createPathProperties().getProperty(PathProperties.ADMIN_PAGE);

    }

    private Category getCategory(int langId) throws ServiceException {

        int categoryId;
        if (session == null) {
            session = request.getSession();
        }

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
            category = siteLogic.getCategoryById(categoryId);
        } catch (ServiceException e) {
            log.error("Can't get category by id " + categoryId, e);
            throw new ServiceException("Can't get category by id " + categoryId, e);
        }

        return category;
    }
}
