package by.academy.logic;

import by.academy.dao.*;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 */
public class SiteLogic extends DataAccessService {

	private static Log log = LogFactory.getLog(SiteLogic.class);

	public SiteLogic() throws ServiceException {
		super();
	}

	public Performance getPerformancesById(int id, int langId)
			throws ServiceException {
		IPerformanceDao perfDao = daoFactory.getPerformanceDao();
		Performance perf = null;
		try {
			perf = perfDao.getEntityById(id);
			sortPropertyByLang(perf.getProperties(), langId);
		} catch (DaoException e) {
			log.error(
					"DaoException in SiteLogic. Can't collect Performance for id"
							+ id, e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Performance for id"
							+ id, e);
		}
		return perf;
	}

	public List<Performance> getPerformancesByCategory(Integer categoryId,
			Integer langId) throws ServiceException {
		Category selectedCategory = getCategoryById(categoryId);
		Set<Performance> perfSet = selectedCategory.getPerformances();
		for (Performance p : perfSet) {
			sortPropertyByLang(p.getProperties(), langId);
		}
		List<Performance> perfList = new ArrayList<>();
		perfList.addAll(perfSet);
		Collections.sort(perfList);
		return perfList;
	}

	public List<Performance> getAllPerformances(Integer langId)
			throws ServiceException {
		IPerformanceDao perfDao = daoFactory.getPerformanceDao();
		Set<Performance> performances = null;
		try {
			performances = new HashSet<Performance>(perfDao.findAll());
			log.info("Performances count ="+performances.size());
			for (Performance p : performances) {
				sortPropertyByLang(p.getProperties(), langId);
			}
		} catch (DaoException e) {
			log.error("DaoException in SiteLogic. Can't collect Performances",
					e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Performances", e);
		}
		List<Performance> perfList = new ArrayList<>();
		perfList.addAll(performances);
		Collections.sort(perfList);
		return perfList;
	}

	Integer sortPropertyByLang(Set<Property> propSet, Integer langId) {
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
		IEventDao eventDao = daoFactory.getEventDao();
		Event event = null;
		try {
			event = eventDao.getEntityById(id);
		} catch (DaoException e) {
			log.error("DaoException in SiteLogic. Can't collect Event for id "
					+ id, e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Event for id "
							+ id, e);
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
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Events", e);
		}
		Collections.sort(events);
		return events;
	}

	public List<Event> getEventsInDateInterval(Calendar begin, Calendar end,
			Integer langId) throws ServiceException {
		try {
			IEventDao eventDao = daoFactory.getEventDao();
			List<Event> events = eventDao.getEventsInDateInterval(
					begin.getTimeInMillis(), end.getTimeInMillis());

			for (Event e : events) {
				log.info("Event Id: " + e.getId() + "; " + "Start time: "
						+ e.getStartTime() + "; " + "Start time: "
						+ e.getEndTime() + "; " + "Perf ID: "
						+ e.getPerformance().getId());
				sortPropertyByLang(e.getPerformance().getProperties(), langId);
				e.setFreeTicketsCount(getFreeTickets(e).size());

				List<Ticket> ticketsList = setPricesForTikets(new ArrayList<Ticket>(
						e.getTickets()));
				Integer[] maxAndMinPrices = getMaxAndMinTicketPrice(ticketsList);
				e.setMaxTicketPrice(maxAndMinPrices[1]);
				e.setMinTicketPrice(maxAndMinPrices[0]);
			}
			Collections.sort(events);
			return events;
		} catch (DaoException e) {
			log.error("DaoException in SiteLogic. Can't collect Events", e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Events", e);
		}
	}

	public List<Event> sortEventsByCategory(List<Event> events,
			Category category) {

		List<Event> sortedEvents = new ArrayList<Event>();

		for (Event event : events) {
			log.info("id категории ивента: "
					+ event.getPerformance().getCategory().getId());
			log.info("id категории фильтра: " + category.getId());
			if (event.getPerformance().getCategory().getId()
					.equals(category.getId())) {
				sortedEvents.add(event);
			}
		}
		Collections.sort(sortedEvents);
		return sortedEvents;

	}

	public List<Category> getAllCategories(Integer langId)
			throws ServiceException {
		ICategoryDao categoryDao = daoFactory.getCategoryDao();
		List<Category> categories;
		try {
			categories = categoryDao.getParentEntities();
			for (Category c : categories) {
				Set<Category> categoriesSortedByLang = new HashSet<Category>();
				for (Category childCategory : c.getChildCategories()) {
					if (childCategory.getLangId().equals(langId)) {
						categoriesSortedByLang.add(childCategory);
					}
				}
				c.setChildCategories(categoriesSortedByLang);
			}
		} catch (DaoException e) {
			log.error("DaoException in SiteLogic. Can't collect Categories", e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Categories", e);
		}

		return categories;

	}

	public Category getCategoryById(Integer id) throws ServiceException {
		ICategoryDao categoryDao = daoFactory.getCategoryDao();
		Category category = null;
		try {
			category = categoryDao.getEntityById(id);
		} catch (DaoException e) {
			log.error(
					"DaoException in SiteLogic. Can't collect Category for id "
							+ id, e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Category for id "
							+ id, e);
		}

		return category;

	}

	public List<Ticket> getTicketsByEvent(Event event) throws ServiceException {
		ITicketDao ticketDao = daoFactory.getTicketDao();

		List<Ticket> tickets = null;
		try {
			tickets = ticketDao.getTicketsByEvent(event);
		} catch (DaoException e) {
			log.error("DaoException in SiteLogic. Can't collect Tickets", e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Tickets", e);
		}

		return tickets;

	}

	public List<Ticket> setPricesForTikets(List<Ticket> ticketsList)
			throws ServiceException {
		if (ticketsList.isEmpty()) {
			return new ArrayList<Ticket>();
		}
		Performance performance = ticketsList.get(0).getEvent()
				.getPerformance();
		// getting list of TicketPrices for performance (one element for each
		// price category)
		Set<TicketsPrice> ticketsPriceList = performance.getTicketsPrices();

		for (TicketsPrice tp : ticketsPriceList) {
			log.info("Tickets prices list=" + tp.getPriceCategory() + "-"
					+ tp.getPrice());
			for (Ticket ticket : ticketsList) {
				/* if(tp.getSeats().contains(ticket.getPlace())) */
				if (tp.getPriceCategory() == ticket.getPlace()
						.getPriceCategory()) {
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

	public List<Ticket> getTicketsByStatusId(Event event, Integer statusId)
			throws ServiceException {
		ITicketDao ticketDao = daoFactory.getTicketDao();
		IStatusDao statusDao = daoFactory.getStatusDao();

		Status status = null;
		List<Ticket> tickets = null;
		try {
			status = statusDao.getEntityById(statusId);
			tickets = ticketDao.getTicketsByStatus(event, status);
		} catch (DaoException e) {
			log.error("DaoException in SiteLogic. Can't collect Tickets", e);
			throw new ServiceException(
					"DaoException in SiteLogic. Can't collect Tickets", e);
		}

		return tickets;

	}

	public List<Ticket> getFreeTickets(Event event) throws ServiceException {

		Integer freeTicketsStatus = 1;

		List<Ticket> tickets = null;
		try {
			tickets = getTicketsByStatusId(event, freeTicketsStatus);
		} catch (ServiceException e) {
			log.error("ServiceException in SiteLogic. Can't collect Tickets", e);
			throw new ServiceException(
					"ServiceException in SiteLogic. Can't collect Tickets", e);
		}

		return tickets;
	}

	public Integer[] getMaxAndMinTicketPrice(List<Ticket> tickets) {
		if (tickets.isEmpty()) {
			return null;
		}
		Integer [] maxAndMinPrices = new Integer [2];
		
		Integer maxPrice = tickets.get(0).getPrice();
		Integer minPrice = tickets.get(0).getPrice();
		
		for (Ticket ticket : tickets) {
			if (ticket.getPrice() > maxPrice) {
				maxPrice = ticket.getPrice();
			}
			if (ticket.getPrice() < minPrice) {
				minPrice = ticket.getPrice();
			}
		}
		maxAndMinPrices[0]= minPrice;
		maxAndMinPrices[1] = maxPrice;
		return maxAndMinPrices;
	}

	
}
