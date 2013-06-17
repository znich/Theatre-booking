package by.academy.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import by.academy.dao.ICategoryDao;
import by.academy.dao.IEventDao;
import by.academy.dao.IPerformanceDao;
import by.academy.dao.IStatusDao;
import by.academy.dao.ITicketDao;
import by.academy.dao.ITicketsPriceDao;
import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.domain.Status;
import by.academy.domain.Ticket;
import by.academy.domain.TicketsPrice;
import by.academy.exception.ServiceException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class SiteLogic extends DataAccessService {

    public SiteLogic()   {
        super();
    }

    public Performance getPerformancesById(int id, int langId) {
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        Performance perf = perfDao.getEntityById(id);
        return perf;
    }

    public List<Performance> getPerformancesByCategory(Integer categoryId) {
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        List<Performance> perfList = perfDao.getPerformancesByCategory(categoryId);
        return perfList;
    }

    
    public List<Performance> getAllPerformances(Integer langId) {
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        List<Performance> performances = perfDao.findAll();
        return performances;
    }

    public Event getEventById(int id, int langId) {
        IEventDao eventDao = daoFactory.getEventDao();
        Event event = eventDao.getEntityById(id);
        return event;
    }
    
    public List<Event> getAllEvents(Integer langId) {
        IEventDao eventDao = daoFactory.getEventDao();
        List<Event> events = eventDao.findAll();
        return events;
    }

    public List<Event> getEventsInDateInterval(Calendar begin, Calendar end, int langID) {
        IEventDao eventDao = daoFactory.getEventDao();
        List<Event> events = eventDao.getEventsInDateInterval(begin, end);
        
        
        for (Event event : events){
        	List<Ticket> freeTickets = getFreeTickets(event);
        	freeTickets = setPricesForTikets(freeTickets);
        	event.setFreeTicketsCount(freeTickets.size());
        	event.setMaxTicketPrice(getMaxTicketPrice(freeTickets));
        	event.setMinTicketPrice(getMinTicketPrice(freeTickets));
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

    public List<Category> getAllCategories(Integer langId) {
        ICategoryDao categoryDao = daoFactory.getCategoryDao();
        List<Category> categories = categoryDao.findAll(langId);

        return categories;

    }

    public Category getCategoryById(Integer id, Integer langId) {
        ICategoryDao categoryDao = daoFactory.getCategoryDao();
        Category category = categoryDao.getEntityById(id, langId);

        return category;

    }

    public List<TicketsPrice> getTicketsPriceByPerformance(Performance performance) {
        ITicketsPriceDao ticketsPriceDao = daoFactory.getTicketsPriceDao();

        List<TicketsPrice> ticketsPrices = ticketsPriceDao.getTicketsPriceForPerformance(performance);

        return ticketsPrices;

    }
    
    public List<Ticket> getTicketsByEvent(Event event){
    	ITicketDao ticketDao = daoFactory.getTicketDao();
    	
    	List<Ticket> tickets = ticketDao.getTicketsByEvent(event);
		
    	return tickets;
    	
    	
    	
    }
    
    public List<Ticket> setPricesForTikets (List<Ticket> ticketsList){
    	
    	Performance performance = ticketsList.get(0).getEvent().getPerformance();
    	
    	List<TicketsPrice> ticketsPriceList = getTicketsPriceByPerformance (performance);
    	
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
    
    public List<Ticket> getTicketsByStatusId (Event event, Integer statusId){
    	ITicketDao ticketDao = daoFactory.getTicketDao();
    	IStatusDao statusDao = daoFactory.getStatusDao();
    	
    	Status status = statusDao.getEntityById(statusId);
    	
    	List<Ticket> tickets = ticketDao.getTicketsByStatus(event,status);
    	
    	return tickets;
    	
    }
    
    
    public List<Ticket> getFreeTickets (Event event){
    	
    	Integer freeTicketsStatus = 1;    	
    	
    	List<Ticket> tickets = getTicketsByStatusId(event, freeTicketsStatus);
    	
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
