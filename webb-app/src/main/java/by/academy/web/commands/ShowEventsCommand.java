package by.academy.web.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

public class ShowEventsCommand implements ICommand {
    private static Log log = LogFactory.getLog(ShowEventsCommand.class);
    private HttpServletRequest request;
    private SiteLogic siteLogic;
    private HttpSession session;


    public ShowEventsCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.session = wrapper.getSession();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {

        int langId = (Integer) session.getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String dateInterval;

        if (request.getParameter(RequestConstants.DATE_INTERVAL.getName()) != null) {

            dateInterval = request.getParameter(RequestConstants.DATE_INTERVAL.getName());

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
                throw new ServiceException("Wrong date format error", e);
            }

        } else {
            begin.add(Calendar.DAY_OF_YEAR, -14);
            end.add(Calendar.DAY_OF_YEAR, 14);
        }
        List<Event> eventList;
        try {
            eventList = siteLogic.getEventsInDateInterval(begin, end, langId);
        } catch (ServiceException e) {
            log.error("Can't get events", e);
            throw new ServiceException("Can't get events", e);
        }
        Category category = getCategory(langId);
        if (category != null && category.getId() != 0) {

            List<Event> sortedEventList = siteLogic.sortEventsByCategory(eventList, category);
            eventList = sortedEventList;
        }

        request.setAttribute(RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(), eventList);
        try {
            session.setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteLogic.getAllCategories(langId));
        } catch (ServiceException e) {
            log.error("Can't get categories", e);
            throw new ServiceException("Can't get categories", e);
        }

        return PathProperties.createPathProperties().getProperty(PathProperties.EVENTS_LIST_PAGE);

    }

    private Category getCategory(int langId) throws ServiceException {

        Integer categoryId;


        if (request.getParameter(RequestConstants.CATEGORY_ID.getName()) != null) {

            categoryId = Integer.parseInt(request.getParameter(RequestConstants.CATEGORY_ID.getName()));

            session.setAttribute(RequestConstants.CATEGORY_ID.getName(), categoryId);
        }

        if (request.getSession().getAttribute(RequestConstants.CATEGORY_ID.getName()) == null) {
            categoryId = 0;
        } else {
            categoryId = (Integer) session.getAttribute(RequestConstants.CATEGORY_ID.getName());
        }

        Category category;
        try {
            category = siteLogic.getCategoryById(categoryId);
        } catch (ServiceException e) {
            log.error("Can't get category by id", e);
            throw new ServiceException("Can't get category by id", e);
        }

        return category;
    }
}
