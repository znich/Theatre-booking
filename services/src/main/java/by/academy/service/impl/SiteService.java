package by.academy.service.impl;

import by.academy.dao.*;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import by.academy.service.ISiteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 */
public class SiteService implements ISiteService {

    private static Log log = LogFactory.getLog(SiteService.class);
    private IGenericDao<Performance, Integer> perfDao;
    private IGenericDao<Event, Integer>  eventDao;
    private IGenericDao<Category, Integer>  categoryDao;
    private IGenericDao<Ticket, Integer>  ticketDao;
    private IGenericDao<Status, Integer>  statusDao;

    public void setPerfDao(IGenericDao<Performance, Integer> perfDao) {
        this.perfDao = perfDao;
    }

    public void setEventDao(IGenericDao<Event, Integer> eventDao) {
        this.eventDao = eventDao;
    }

    public void setCategoryDao(IGenericDao<Category, Integer> categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setTicketDao(IGenericDao<Ticket, Integer> ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void setStatusDao(IGenericDao<Status, Integer> statusDao) {
        this.statusDao = statusDao;
    }

    public Performance getPerformancesById(Integer id, Integer langId) throws ServiceException {
        try {
            Performance perf = perfDao.getEntityById(id);
            sortPropertyByLang(perf.getProperties(), langId);
            return perf;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Performance for id" + id, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Performance for id" + id, e);
        }
    }

    public Set<Performance> getPerformancesByCategory(Integer categoryId, Integer langId) throws ServiceException {
        Category selectedCategory = getCategoryById(categoryId);
        Set<Performance> perfSet = selectedCategory.getPerformances();
        for (Performance p : perfSet) {
            sortPropertyByLang(p.getProperties(), langId);
        }
        return perfSet;
    }

    public List<Performance> getAllPerformances(Integer langId) throws ServiceException {
        try {
            Set<Performance> performances = new HashSet<Performance>(perfDao.findAll());
            for (Performance p : performances) {
                sortPropertyByLang(p.getProperties(), langId);
            }
            return performances;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Performances", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Performances", e);
        }
    }

     public Integer sortPropertyByLang(Set<Property> propSet, Integer langId) {
    	 Integer result = 0;
    	 for (Property prop : propSet) {
            Set<Property> sortedProperties = new HashSet<Property>();
            for (Property childProp : prop.getChildProperties()) {
                if (childProp.getLangId().equals(langId)) {
                	if (sortedProperties.add(childProp)) {
						result++;
					}
                }
            }
            prop.setChildProperties(sortedProperties);
        }
        return result;
    }

    public Event getEventById(int id, int langId) throws ServiceException {
        try {
            Event event = eventDao.getEntityById(id);
            return event;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Event for id " + id, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Event for id " + id, e);
        }
    }

    public List<Event> getAllEvents(int langId) throws ServiceException {
        try {
            List<Event> events = eventDao.findAll();
            return events;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Events", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Events", e);
        }
    }

    public List<Event> getEventsInDateInterval(Calendar begin, Calendar end, Integer langId) throws ServiceException {
        try {
            List<Event> events = eventDao.findByCriteria(Restrictions.between("startTime", begin.getTimeInMillis(), end.getTimeInMillis()));

            for (Event e : events) {
                log.info("Event Id: " + e.getId() + "; " + "Start time: " + e.getStartTime() + "; " + "Start time: " + e.getEndTime() + "; " + "Perf ID: " + e.getPerformance().getId());
                sortPropertyByLang(e.getPerformance().getProperties(), langId);
                e.setFreeTicketsCount(getFreeTickets(e).size());

                List<Ticket> ticketsList = setPricesForTikets(new ArrayList<Ticket>(e.getTickets()));
                e.setMaxTicketPrice(getMaxTicketPrice(ticketsList));
                e.setMinTicketPrice(getMinTicketPrice(ticketsList));
            }

            return events;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Events", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Events", e);
        }
    }

    public List<Event> sortEventsByCategory(List<Event> events, Category category) {

        List<Event> sortedEvents = new ArrayList<Event>();

        for (Event event : events) {
            log.info("id категории ивента: " + event.getPerformance().getCategory().getId());
            log.info("id категории фильтра: " + category.getId());
            if (event.getPerformance().getCategory().getId().equals(category.getId())) {
                sortedEvents.add(event);
            }
        }
        return sortedEvents;

    }

    public List<Category> getAllCategories(Integer langId) throws ServiceException {
        try {
            List<Category> categories = categoryDao.getParentEntities();
            for (Category c : categories) {
                Set<Category> categoriesSortedByLang = new HashSet<Category>();
                for (Category childCategory : c.getChildCategories()) {
                    if (childCategory.getLangId().equals(langId)) {
                        categoriesSortedByLang.add(childCategory);
                    }
                }
                c.setChildCategories(categoriesSortedByLang);
            }
            return categories;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Categories", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Categories", e);
        }
    }

    public Category getCategoryById(Integer id) throws ServiceException {
        try {
            Category category = categoryDao.getEntityById(id);
            return category;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Category for id " + id, e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Category for id " + id, e);
        }
    }

    public List<Ticket> getTicketsByEvent(Event event) throws ServiceException {
        try {
            List<Ticket> tickets = ticketDao.findByCriteria(Restrictions.eq("event", event));
            return tickets;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Tickets", e);
        }
    }

    public List<Ticket> setPricesForTikets(List<Ticket> ticketsList) throws ServiceException {
        if (ticketsList.isEmpty()) {
            return new ArrayList<Ticket>();
        }
        Performance performance = ticketsList.get(0).getEvent().getPerformance();
        Set<TicketsPrice> ticketsPriceList = performance.getTicketsPrices();

        for (TicketsPrice tp : ticketsPriceList) {
            for (Ticket ticket : ticketsList) {
                if (tp.getSeats().contains(ticket.getPlace())) {
                    ticket.setPrice(tp.getPrice());
                }
            }
        }

        return ticketsList;
    }

    public List<Ticket> sortTicketsByStatus(List<Ticket> tickets, Status status) {

        List<Ticket> sortedTickest = new ArrayList<Ticket>();
        for (Ticket ticket : tickets) {
            if (ticket.getStatus().equals(status)) {
                sortedTickest.add(ticket);
            }
        }

        return sortedTickest;


    }

    public List<Ticket> getTicketsByStatusId(Event event, Integer statusId) throws ServiceException {
        try {
            Status status = statusDao.getEntityById(statusId);
            Criterion ticketByStatusCondition =
                    Restrictions.conjunction().add(Restrictions.eq("event", event))
                            .add(Restrictions.eq("status", status));
            List<Ticket> tickets = ticketDao.findByCriteria(ticketByStatusCondition);
            return tickets;
        } catch (DaoException e) {
            log.error("DaoException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("DaoException in SiteLogic. Can't collect Tickets", e);
        }
    }

    public List<Ticket> getFreeTickets(Event event) throws ServiceException {
        try {
            Integer freeTicketsStatus = 1;
            List<Ticket> tickets = getTicketsByStatusId(event, freeTicketsStatus);
            return tickets;
        } catch (ServiceException e) {
            log.error("ServiceException in SiteLogic. Can't collect Tickets", e);
            throw new ServiceException("ServiceException in SiteLogic. Can't collect Tickets", e);
        }
    }

    public Integer getMaxTicketPrice(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            return 0;
        }
        Integer maxPrice = tickets.get(0).getPrice();
        for (Ticket ticket : tickets) {
            if (ticket.getPrice() > maxPrice) {
                maxPrice = ticket.getPrice();
            }
        }
        return maxPrice;
    }

    public Integer getMinTicketPrice(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            return 0;
        }
        Integer minPrice = tickets.get(0).getPrice();
        for (Ticket ticket : tickets) {
            if (ticket.getPrice() < minPrice) {
                minPrice = ticket.getPrice();
            }
        }
        return minPrice;
    }

    public SortedMap<Integer, SortedMap<Integer, List<Integer>>> sortTicketsBySectorAndRows(List<Ticket> availableTicketsForEvent) {
        Set<Integer> sectors = new HashSet<Integer>();
        Set<Integer> rows;
        List<Integer> seats;
        SortedMap<Integer, SortedMap<Integer, List<Integer>>> result = new TreeMap<Integer, SortedMap<Integer, List<Integer>>>();
        Map<Integer, Set<Integer>> curRows = new TreeMap<Integer, Set<Integer>>();
        for (Ticket t : availableTicketsForEvent) {
            sectors.add(t.getPlace().getSector());

        }

        for (Integer i : sectors) {
            SortedMap<Integer, List<Integer>> curSeats = new TreeMap<Integer, List<Integer>>();
            result.put(i, curSeats);
            rows = new TreeSet<Integer>();
            for (Ticket t : availableTicketsForEvent) {
                if (i.equals(t.getPlace().getSector())) {
                    rows.add(t.getPlace().getRow());
                }
            }
            curRows.put(i, rows);
        }

        for (Integer i : sectors) {
            for (Integer j : curRows.get(i)) {
                seats = new ArrayList<Integer>();
                for (Ticket t : availableTicketsForEvent) {
                    if (t.getPlace().getSector() == i.intValue() && t.getPlace().getRow() == j.intValue()) {
                        seats.add(t.getPlace().getSeatNumber());

                    }
                }
                List<Integer> newseats = new ArrayList<>(seats);
                Collections.sort(newseats);
                seats.clear();
                seats.addAll(newseats);


                result.get(i).put(j, seats);
            }
        }
        return result;
    }

	

	@Override
	public Event getEventById(Integer eventId, int langId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
