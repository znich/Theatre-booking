package by.academy.web.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShowEventsCommand implements ICommand {
    private static Log log = LogFactory.getLog(ShowEditPerformanceCommand.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SiteLogic siteLogic;
    private List<String> rights = new ArrayList<String>();


    public ShowEventsCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {

        int langId = (Integer) request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        String dateFirst;
        String dateLast;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String dateInterval = "";

        if (request.getParameter(SessionConstants.DATE_INTERVAL.getName()) != null) {

            dateInterval = request.getParameter(SessionConstants.DATE_INTERVAL.getName());

            request.getSession().setAttribute(SessionConstants.DATE_INTERVAL.getName(), dateInterval);
        }

        dateInterval = (String) request.getSession().getAttribute(SessionConstants.DATE_INTERVAL.getName());
        if (dateInterval != null && dateInterval.length() > 0) {
            String[] dates = dateInterval.split(" - ");
            dateFirst = dates[0];
            dateLast = dates[1];

            try {
                date1.setTime(sdf.parse(dateFirst));
                date2.setTime(sdf.parse(dateLast));
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new ServiceException("Wrong date format error", e);
            }

        } else {
            date2.add(Calendar.MONTH, 1);
        }

        List<Event> eventList = null;
        try {
            eventList = siteLogic.getEventsInDateInterval(date1, date2, langId);
        } catch (ServiceException e) {
            log.error("Can't get events", e);
            throw new ServiceException("Can't get events", e);
        }
        Category category = getCategory(langId);

        if (category != null && category.getId() != 0) {

            List<Event> sortedEventList = siteLogic.sortEventsByCategory(eventList, category);
            eventList = sortedEventList;
        }

        request.setAttribute(SessionConstants.EVENTS_LIST_ATTRIBUTE.getName(), eventList);
        try {
            request.getSession().setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteLogic.getAllCategories(langId));
        } catch (ServiceException e) {
            log.error("Can't get categories", e);
            throw new ServiceException("Can't get categories", e);
        }

        return PathProperties.createPathProperties().getProperty(PathProperties.EVENTS_LIST_PAGE);

    }

    private Category getCategory(int langId) throws ServiceException {

        int categoryId;


        if (request.getParameter(SessionConstants.CATEGORY_ID.getName()) != null) {

            categoryId = Integer.parseInt(request.getParameter(SessionConstants.CATEGORY_ID.getName()));

            request.getSession().setAttribute(SessionConstants.CATEGORY_ID.getName(), categoryId);
        }

        if (request.getSession().getAttribute(SessionConstants.CATEGORY_ID.getName()) == null) {
            categoryId = 0;
        } else {
            categoryId = (Integer) request.getSession().getAttribute(SessionConstants.CATEGORY_ID.getName());
        }

        Category category = null;
        try {
            category = siteLogic.getCategoryById(categoryId, langId);
        } catch (ServiceException e) {
            log.error("Can't get category by id", e);
            throw new ServiceException("Can't get category by id", e);
        }

        return category;
    }
}
