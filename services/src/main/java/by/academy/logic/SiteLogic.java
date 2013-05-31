package by.academy.logic;

import by.academy.DAO.category.CategoryDAO;
import by.academy.DAO.event.EventDAO;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory.DAOFactory;
import by.academy.DAO.performance.PerformanceDAO;
import by.academy.Model.CategoryData;
import by.academy.Model.EventData;
import by.academy.Model.PerformanceData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class SiteLogic {

    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.DAO Factory
    PerformanceDAO perfDAO = null;
    EventDAO eventDAO = null;
    CategoryDAO categoryDAO = null;

    public SiteLogic() {
        AdminLogic admLogic = new AdminLogic();
        admLogic.deleteExpiredBookings();
    }

    public PerformanceData getPerformancesById(int id, int langId) {

        try {
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        PerformanceData perf = perfDAO.getPerformanceById(id, langId);
        return perf;
    }

    public List<PerformanceData> getPerformancesByCategory(int categoryId, int langId) {

        try {
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        ArrayList<PerformanceData> perfList = perfDAO.getPerformancesByCategory(categoryId, langId);
        return perfList;
    }

    public ArrayList<PerformanceData> getAllPerformances(int langId) {

        try {
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        ArrayList<PerformanceData> performances = perfDAO.getAllPerformances(langId);
        return performances;
    }

    public List<EventData> getAllEvents(int langId) {

        try {
            eventDAO = oracleFactory.getEventDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        List<EventData> events = eventDAO.getAllEvents(langId);
        return events;
    }

    public List<EventData> getEventsInDateInterval(Date date1, Date date2, int langID) {

        try {
            eventDAO = oracleFactory.getEventDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }
        long begin;
        long end;
        if (date1.before(date2)) {
            begin = date1.getTime();
            end = date2.getTime();
        } else {
            begin = date2.getTime();
            end = date1.getTime();
        }
        List<EventData> events = eventDAO.getEventsInDateInterval(begin, end, langID);
        return events;
    }

    public List<EventData> sortEventsByCategory(List<EventData> events, CategoryData category) {

        List<EventData> sortedEvents = new ArrayList<EventData>();

        for (EventData event : events) {
            if (event.getPerformance().getCategory().equals(category)) {
                sortedEvents.add(event);
            }
        }
        return sortedEvents;

    }

    public List<CategoryData> getAllCategories(int langId) {

        try {
            categoryDAO = oracleFactory.getCategoryDAO();
        } catch (CannotTakeConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<CategoryData> categories = categoryDAO.getAllCategories(langId);

        return categories;

    }

    public CategoryData getCategoryById(int id, int langId) {

        try {
            categoryDAO = oracleFactory.getCategoryDAO();
        } catch (CannotTakeConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CategoryData category = categoryDAO.getCategoryById(id, langId);

        return category;

    }

}
