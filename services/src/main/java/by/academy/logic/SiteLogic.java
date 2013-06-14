package by.academy.logic;

import by.academy.dao.ICategoryDao;
import by.academy.dao.IEventDao;
import by.academy.dao.IPerformanceDao;
import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.exception.ServiceException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class SiteLogic extends DataAccessService {
    IPerformanceDao perfDao = daoFactory.getPerformanceDao();
    IEventDao eventDao = daoFactory.getEventDao();
    ICategoryDao categoryDao = daoFactory.getCategoryDao();

    public SiteLogic() throws ServiceException {
        super();
    }

    public Performance getPerformancesById(int id, int langId) {

        Performance perf = perfDao.getEntityById(id);
        return perf;
    }

    public List<Performance> getPerformancesByCategory(Integer categoryId) {

        List<Performance> perfList = perfDao.getPerformancesByCategory(categoryId);
        return perfList;
    }

    public List<Performance> getAllPerformances(Integer langId) {

        List<Performance> performances = perfDao.findAll();
        return performances;
    }

    public List<Event> getAllEvents(Integer langId) {

        List<Event> events = eventDao.findAll();
        return events;
    }

    public List<Event> getEventsInDateInterval(Calendar begin, Calendar end, int langID) {

        List<Event> events = eventDao.getEventsInDateInterval(begin, end);
        return events;
    }

    public List<Event> sortEventsByCategory(List<Event> events, Category category) {

        List<Event> sortedEvents = new ArrayList<Event>();

        for (Event event : events) {
            if (event.getPerformance().getCategory().equals(category)) {
                sortedEvents.add(event);
            }
        }
        return sortedEvents;

    }

    public List<Category> getAllCategories(Integer langId) {

        List<Category> categories = categoryDao.findAll(langId);

        return categories;

    }

    public Category getCategoryById(Integer id, Integer langId) {

        Category category = categoryDao.getEntityById(id, langId);

        return category;

    }

    public List<TicketsPriceData> getTicketsPriceByPerformance(PerformanceData performance) {
        try {
            ticketsPriceDAO = oracleFactory.getTicketsPriceDAO();
        } catch (CannotTakeConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<TicketsPriceData> ticketsPrices = ticketsPriceDAO.getTicketsPriceForPerformance(performance);

        return ticketsPrices;

    }

}
