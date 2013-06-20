package by.academy.logic;

import by.academy.dao.*;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class SiteLogic extends DataAccessService {

    private static Log log = LogFactory.getLog(SiteLogic.class);

    public SiteLogic() throws ServiceException {
        super();
    }

    public Performance getPerformancesById(int id, int langId) throws ServiceException {
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        Performance perf = null;
        try {
            perf = perfDao.getEntityById(id);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Performance for id" + id, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Performance for id" + id, e);
        }
        return perf;
    }

    public List<Performance> getPerformancesByCategory(Integer categoryId) throws ServiceException {
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        List<Performance> perfList = null;
        try {
            perfList = perfDao.getPerformancesByCategory(categoryId);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Performances for category" + categoryId, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Performance for id" + categoryId, e);
        }
        return perfList;
    }

    public Set<Performance> getAllPerformances(Integer langId) throws ServiceException {
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        log.info("LangId = " + langId);
        Set<Performance> performances = null;
        try {
            performances = new HashSet<Performance>(perfDao.findAll());
            for(Performance p: performances){
                log.info("performance ID: " + p.getId());
                for(Property prop: p.getProperties()){
                    Set<Property> sortedProperties = new HashSet<Property>();
                    for(Property childProp: prop.getChildProperties()){
                        log.info(childProp.getValue() + "; " + childProp.getLangId());
                        if (childProp.getLangId().equals(langId)){
                            log.info("этот пропети попал в отсортированный сет = " + childProp.getValue());
                            sortedProperties.add(childProp);
                        }
                    }
                    prop.setChildProperties(sortedProperties);
                }
            }
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Performances", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Performances", e);
        }
        //log.info(performances.toString());
        return performances;
    }

    public Event getEventById(int id, int langId) throws ServiceException {
        IEventDao eventDao = daoFactory.getEventDao();
        Event event = null;
        try {
            event = eventDao.getEntityById(id);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Event for id " + id, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Event for id " + id, e);
        }
        return event;
    }

    public List<Event> getAllEvents(Integer langId) throws ServiceException {
        IEventDao eventDao = daoFactory.getEventDao();
        List<Event> events = null;
        try {
            events = eventDao.findAll();
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Events", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Events", e);
        }
        return events;
    }

    public List<Event> getEventsInDateInterval(Calendar begin, Calendar end, int langID) throws ServiceException {
        IEventDao eventDao = daoFactory.getEventDao();
        List<Event> events = null;
        try {
            events = eventDao.getEventsInDateInterval(begin, end);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Events", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Events", e);
        }
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

    public List<Category> getAllCategories(Integer langId) throws ServiceException {
        ICategoryDao categoryDao = daoFactory.getCategoryDao();
        List<Category> categories = new ArrayList<Category>();
        try {
            categories = categoryDao.findAll(langId);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Categories", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Categories", e);
        }

        return categories;

    }

    public Category getCategoryById(Integer id, Integer langId) throws ServiceException {
        ICategoryDao categoryDao = daoFactory.getCategoryDao();
        Category category = null;
        try {
            category = categoryDao.getEntityById(id, langId);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Category for id " + id, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Category for id " + id, e);
        }

        return category;

    }

    public List<TicketsPrice> getTicketsPriceByPerformance(Performance performance) throws ServiceException {
        ITicketsPriceDao ticketsPriceDao = daoFactory.getTicketsPriceDao();

        List<TicketsPrice> ticketsPrices = null;
        try {
            ticketsPrices = ticketsPriceDao.getTicketsPriceForPerformance(performance);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect TicketsPrice", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect TicketsPrice", e);
        }

        return ticketsPrices;

    }

    public List<Ticket> getTicketsByEvent(Event event) throws ServiceException {
        ITicketDao ticketDao = daoFactory.getTicketDao();

        List<Ticket> tickets = null;
        try {
            tickets = ticketDao.getTicketsByEvent(event);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Tickets", e);
        }

        return tickets;



    }

    public List<Ticket> setPricesForTikets (List<Ticket> ticketsList) throws ServiceException {

        Performance performance = ticketsList.get(0).getEvent().getPerformance();

        List<TicketsPrice> ticketsPriceList = null;
        try {
            ticketsPriceList = getTicketsPriceByPerformance (performance);
        } catch (ServiceException e) {
            log.error("ServiceException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("ServiceException in SiteLogic. Can't collect Tickets", e);
        }

        Integer [] PerformancePrices = new Integer [ticketsPriceList.size()];

        for (TicketsPrice ticketPrice : ticketsPriceList){
            Integer price = ticketPrice.getPrice();
            PerformancePrices[ticketPrice.getPriceCategory()] = price;
        }

        for (Ticket ticket : ticketsList){


            ticket.setPrice(PerformancePrices[ticket.getPlace().getPriceCategory()]);
        }

        return ticketsList;
    }

    public List<Ticket> sortTicketsByStatus (List<Ticket> tickets, Status status){

        List<Ticket> sortedTickest = new ArrayList<Ticket>();
        for (Ticket ticket : tickets){
            if (ticket.getStatus().equals(status)){
                sortedTickest.add(ticket);
            }
        }

        return sortedTickest;


    }

    public List<Ticket> getTicketsByStatusId (Event event, Integer statusId) throws ServiceException {
        ITicketDao ticketDao = daoFactory.getTicketDao();
        IStatusDao statusDao = daoFactory.getStatusDao();

        Status status = null;
        List<Ticket> tickets = null;
        try {
            status = statusDao.getEntityById(statusId);
            tickets = ticketDao.getTicketsByStatus(event,status);
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Tickets", e);
        }

        return tickets;

    }


    public List<Ticket> getFreeTickets (Event event) throws ServiceException {

        Integer freeTicketsStatus = 1;

        List<Ticket> tickets = null;
        try {
            tickets = getTicketsByStatusId(event, freeTicketsStatus);
        } catch (ServiceException e) {
            log.error("ServiceException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("ServiceException in SiteLogic. Can't collect Tickets", e);
        }

        return tickets;
    }

    public Integer getMaxTicketPrice (List<Ticket> tickets){

        Integer maxPrice = tickets.get(0).getPrice();
        for (Ticket ticket: tickets){
            if (ticket.getPrice()>maxPrice){
                maxPrice=ticket.getPrice();
            }
        }
        return maxPrice;
    }

    public Integer getMinTicketPrice (List<Ticket> tickets){

        Integer minPrice = tickets.get(0).getPrice();
        for (Ticket ticket: tickets){
            if (ticket.getPrice()<minPrice){
                minPrice=ticket.getPrice();
            }
        }
        return minPrice;
    }
}
