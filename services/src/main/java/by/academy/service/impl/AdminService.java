package by.academy.service.impl;

import java.util.Calendar;

import java.util.List;
import java.util.Set;

import by.academy.dao.*;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import by.academy.service.IAdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */

@Service
public class AdminService implements IAdminService {
    private static Log log = LogFactory.getLog(AdminService.class);

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
                                           Calendar endDate, String image, Category category, Integer langId) throws ServiceException {

        boolean flag = false;
        Performance performance;
        try {
            if (id == null) {
                performance = new Performance();
            } else {
                performance = perfDao.getEntityById(id);
            }
            performance.setStartDate(startDate);
            performance.setEndDate(endDate);
            performance.setCategory(category);

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
                }

                parentProperty.getChildProperties().add(childProperty);
                if (parentProperty.getName() != null) {
                    performance.setProperty(parentProperty);
                }
            }

            if (perfDao.save(performance) != null) {
                flag = true;
            }
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't save Performance", e);
            throw new ServiceException("DaoException in AdminLogic. Can't save Performance", e);
        }
        return flag;
    }

    public boolean deletePerformance(Integer perfId) throws ServiceException {
        boolean flag = false;

        try {
            perfDao.delEntity(perfId);
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete Performance", e);
            throw new ServiceException("DaoException in AdminLogic. Can't delete Performance", e);
        }
        return flag;
    }

    public boolean saveOrUpdateEvent(Integer eventId, int perfId, long startTime, long endTime) throws ServiceException {

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
            throw new ServiceException("DaoException in AdminLogic. Can't save Event", e);
        }
        return flag;
    }

    public int deleteExpiredBookings() throws ServiceException {
        Calendar currentDate = Calendar.getInstance();
        List<Booking> expiredBookingList;

        try {

            expiredBookingList = bookingDao.findByCriteria(Restrictions.lt("expDate", currentDate));
            for (Booking b : expiredBookingList) {
                bookingDao.delEntity(b.getId());

                List<Ticket> tickets = ticketDao.findByCriteria(Restrictions.eq("booking", b));
                for (Ticket ticket : tickets) {

                    ticket.setBooking(null);
                    ticket.setStatus(statusDao.getEntityById(1));
                    ticketDao.save(ticket);
                }
            }
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete Expired Bookings", e);
            throw new ServiceException("DaoException in AdminLogic. Can't delete Expired Bookings", e);
        }
        return expiredBookingList.size();

    }

    public boolean deleteTicketFromBooking(int bookingId, int ticketId, int langId) throws ServiceException {

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
            log.error("DaoException in AdminLogic. Can't delete ticket from booking", e);
            throw new ServiceException("DaoException in AdminLogic. Can't delete ticket from booking", e);
        }
        return flag;
    }

    public boolean editTicketsPriceForPerformance(Set<TicketsPrice> ticketsPrices) throws ServiceException {
        boolean flag = false;
        for (TicketsPrice tp : ticketsPrices) {
            try {
                ticketsPriceDao.save(tp);
            } catch (DaoException e) {
                log.error("DaoException in AdminLogic. Can't save tickets price for performance", e);
                throw new ServiceException("DaoException in AdminLogic. Can't save tickets price for performance", e);
            }
            flag = true;
        }
        return flag;

    }

}
