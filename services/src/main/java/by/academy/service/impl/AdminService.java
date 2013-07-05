package by.academy.service.impl;

import java.util.Calendar;

import java.util.List;
import java.util.Set;

import by.academy.dao.*;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import by.academy.service.IAdminService;
import by.academy.service.ISiteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class AdminService implements IAdminService {
    private static Log log = LogFactory.getLog(AdminService.class);
    @Autowired
    private ISiteService siteService;

    private IGenericDao<Performance, Integer>perfDao;
    private IGenericDao<Status, Integer>statusDao;
    private IGenericDao<Seat, Integer>seatDao;
    private IGenericDao<Event, Integer>eventDao;
    private IGenericDao<Ticket, Integer>ticketDao;
    private IGenericDao<Booking, Integer>bookingDao;
    private IGenericDao<TicketsPrice, Integer>ticketsPriceDao;

    public void setPerfDao(IGenericDao<Performance, Integer> perfDao) {
        this.perfDao = perfDao;
    }

    public void setStatusDao(IGenericDao<Status, Integer> statusDao) {
        this.statusDao = statusDao;
    }

    public void setSeatDao(IGenericDao<Seat, Integer> seatDao) {
        this.seatDao = seatDao;
    }

    public void setEventDao(IGenericDao<Event, Integer> eventDao) {
        this.eventDao = eventDao;
    }

    public void setTicketDao(IGenericDao<Ticket, Integer> ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void setTicketsPriceDao(IGenericDao<TicketsPrice, Integer> ticketsPriceDao) {
        this.ticketsPriceDao = ticketsPriceDao;
    }

    public void setBookingDao(IGenericDao<Booking, Integer> bookingDao) {
        this.bookingDao = bookingDao;
    }
    public boolean saveOrUpdatePerformance(Integer id, String title,
                                           String shortDescription, String description, Calendar startDate,
                                           Calendar endDate, String image, Category category,
                                           Set<TicketsPrice> ticketsPrices, Integer langId)
            throws ServiceException {

        boolean flag = false;
        Performance performance;
        try {
            if (id != null) {

                performance = perfDao.getEntityById(id);
                log.info("Getting performance for editing:"
                        + performance.getId());

                Set<TicketsPrice> pricesList = performance.getTicketsPrices();
                // editing tickets prices for performance
                for (TicketsPrice price : pricesList) {
                    for (TicketsPrice inputPrice : ticketsPrices) {
                        if (price.getPriceCategory() == inputPrice
                                .getPriceCategory()) {
                            price.setPrice(inputPrice.getPrice());
                        }
                    }
                }
                // editing properties for performance
                Set<Property> properties = performance.getProperties();
                Integer result = siteService.sortPropertyByLang(properties,
                        langId);
                // if there's no properties with such language
                if (result <= 0) {
                    addNewPropertiesForPerformance(performance, title,
                            shortDescription, description, image, langId);
                } else {
                    editPropertiesForPerformance(properties, title,
                            shortDescription, description, image);
                }

            } else {
                performance = new Performance();
                performance.setTicketsPrices(ticketsPrices);

                addNewPropertiesForPerformance(performance, title,
                        shortDescription, description, image, langId);
            }

            for (TicketsPrice price : performance.getTicketsPrices()) {
                price.setPerfId(performance);
            }

            performance.setStartDate(startDate);
            performance.setEndDate(endDate);
            performance.setCategory(category);

            if (perfDao.save(performance) != null) {
                flag = true;
            }
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't save Performance", e);
            throw new ServiceException(
                    "DaoException in AdminLogic. Can't save Performance", e);
        }
        return flag;
    }

    private void addNewPropertiesForPerformance(Performance performance,
                                                String title, String shortDescription, String description,
                                                String image, int langId) {

        for (PropertyNameEnum e : PropertyNameEnum.values()) {
            Property parentProperty = new Property();
            Property childProperty = new Property();
            childProperty.setLangId(langId);
            childProperty.setRootProperty(parentProperty);

            switch (e) {
                case NAME:
                    parentProperty.setName(e);
                    childProperty.setName(e);
                    childProperty.setValue(title);
                    break;
                case SHORT_DESCRIPTION:
                    parentProperty.setName(e);
                    childProperty.setName(e);
                    childProperty.setValue(shortDescription);
                    break;
                case DESCRIPTION:
                    parentProperty.setName(e);
                    childProperty.setName(e);
                    childProperty.setValue(description);
                    break;
                case IMAGE:
                    parentProperty.setName(e);
                    childProperty.setName(e);
                    childProperty.setValue(image);
                    break;
                default:
                    break;
            }
            parentProperty.getChildProperties().add(childProperty);
            if (parentProperty.getName() != null) {
                performance.setProperty(parentProperty);

            }
        }

    }

    private void editPropertiesForPerformance(Set<Property> properties,
                                              String title, String shortDescription, String description,
                                              String image) {

        for (Property parentProperty : properties) {
            PropertyNameEnum e = parentProperty.getName();

            switch (e) {

                case NAME:
                    for (Property childProperty : parentProperty
                            .getChildProperties()) {
                        childProperty.setValue(title);
                    }
                    break;
                case SHORT_DESCRIPTION:
                    for (Property childProperty : parentProperty
                            .getChildProperties()) {
                        childProperty.setValue(shortDescription);
                    }
                    break;
                case DESCRIPTION:
                    for (Property childProperty : parentProperty
                            .getChildProperties()) {
                        childProperty.setValue(description);
                    }
                    break;
                case IMAGE:
                    for (Property childProperty : parentProperty
                            .getChildProperties()) {
                        childProperty.setValue(image);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public boolean deletePerformance(Integer perfId) throws ServiceException {
        boolean flag = false;

        try {
            perfDao.delEntity(perfId);
            if(perfDao.getEntityById(perfId) == null ){
                flag = true;
            }

        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete Performance", e);
            throw new ServiceException(
                    "DaoException in AdminLogic. Can't delete Performance", e);
        }
        return flag;
    }

    public boolean saveOrUpdateEvent(Integer eventId, Integer perfId,
                                     long startTime, long endTime) throws ServiceException {

        Event event;

        boolean flag = false;
        try {
            if (eventId == null) {

                event = new Event();

                List<Seat> seats = seatDao.findAll();

                for (Seat s : seats) {
                    Ticket ticket = new Ticket();
                    ticket.setPlace(s);
                    ticket.setStatus(statusDao.getEntityById(1));
                    ticket.setEvent(event);
                    event.getTickets().add(ticket);
                }
            } else {
                event = eventDao.getEntityById(eventId);
            }

            event.setPerformance(perfDao.getEntityById(perfId));
            event.setStartTime(startTime);
            event.setEndTime(endTime);

            if (eventDao.save(event) != null) {
                flag = true;
            }
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't save Event", e);
            throw new ServiceException(
                    "DaoException in AdminLogic. Can't save Event", e);
        }
        return flag;
    }

    public int deleteExpiredBookings() throws ServiceException {
        Calendar currentDate = Calendar.getInstance();
        List<Booking> expiredBookingList;

        try {

            expiredBookingList = bookingDao.findByCriteria(Restrictions.lt(
                    "expDate", currentDate));
            for (Booking b : expiredBookingList) {
                bookingDao.delEntity(b.getId());

                List<Ticket> tickets = ticketDao.findByCriteria(Restrictions
                        .eq("booking", b));
                for (Ticket ticket : tickets) {

                    ticket.setBooking(null);
                    ticket.setStatus(statusDao.getEntityById(1));
                    ticketDao.save(ticket);
                }
            }
        } catch (DaoException e) {
            log.error(
                    "DaoException in AdminLogic. Can't delete Expired Bookings",
                    e);
            throw new ServiceException(
                    "DaoException in AdminLogic. Can't delete Expired Bookings",
                    e);
        }
        return expiredBookingList.size();

    }

    public boolean deleteTicketFromBooking(int bookingId, int ticketId,
                                           int langId) throws ServiceException {

        boolean flag = false;
        try {
            Booking booking = bookingDao.getEntityById(bookingId);
            Ticket ticket = ticketDao.getEntityById(ticketId);

            if (booking != null && ticket == null) {

                booking.deleteTicket(ticket);
                ticket.setStatus(statusDao.getEntityById(1));
                ticket.setBooking(null);
                ticketDao.save(ticket);
                flag = true;
                bookingDao.save(booking);
            }
        } catch (DaoException e) {
            log.error(
                    "DaoException in AdminLogic. Can't delete ticket from booking",
                    e);
            throw new ServiceException(
                    "DaoException in AdminLogic. Can't delete ticket from booking",
                    e);
        }
        return flag;
    }

    public boolean editTicketsPriceForPerformance(
            Set<TicketsPrice> ticketsPrices) throws ServiceException {
        boolean flag = false;
        for (TicketsPrice tp : ticketsPrices) {
            try {
                ticketsPriceDao.save(tp);
            } catch (DaoException e) {
                log.error(
                        "DaoException in AdminLogic. Can't save tickets price for performance",
                        e);
                throw new ServiceException(
                        "DaoException in AdminLogic. Can't save tickets price for performance",
                        e);
            }
            flag = true;
        }
        return flag;

    }

    @Override
    public boolean deleteEvent(Integer eventId) throws ServiceException {
        boolean flag = false;

        try {
            eventDao.delEntity(eventId);
            if(eventDao.getEntityById(eventId) == null){
                flag = true;
            }
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete Event", e);
            throw new ServiceException(
                    "DaoException in AdminLogic. Can't delete Event", e);
        }

        return flag;
    }

}
