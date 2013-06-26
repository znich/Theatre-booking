package by.academy.logic;

import java.util.Calendar;

import java.util.List;

import by.academy.dao.*;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Siarhei Poludvaranin Date: 18.05.13 Time: 15:26 Класс, описывающий
 * логику поведения администратора.
 */
public class AdminLogic extends DataAccessService {
    private static Log log = LogFactory.getLog(AdminLogic.class);

    public AdminLogic() throws ServiceException {
        super();
    }

    public boolean saveOrUpdatePerformance(Integer id, String title,
                                           String shortDescription, String description, Calendar startDate,
                                           Calendar endDate, String image, Category category, int langId) throws ServiceException {

        boolean flag = false;
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
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
                if(parentProperty.getName() != null){
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
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();

        try {
            perfDao.delEntity(perfId);
        } catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete Performance", e);
            throw new ServiceException("DaoException in AdminLogic. Can't delete Performance", e);
        }
        return flag;
    }

    public boolean saveOrUpdateEvent(Integer eventId, int perfId, long startTime, long endTime) throws ServiceException {

        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        IStatusDao statusDao = daoFactory.getStatusDao();
        ISeatDao seatDao = daoFactory.getSeatDao();
        IEventDao eventDao = daoFactory.getEventDao();

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

        ITicketDao ticketDao = daoFactory.getTicketDao();
        IBookingDao bookingDao = daoFactory.getBookingDao();
        IStatusDao statusDao = daoFactory.getStatusDao();
        Calendar currentDate = Calendar.getInstance();
        List<Booking> expiredBookingList;

        try{

        expiredBookingList = bookingDao.getExpiredBooking(currentDate);
        for (Booking b : expiredBookingList) {
            bookingDao.delEntity(b.getId());

            List<Ticket> tickets = ticketDao.getTicketsByBookingId(b);
            for (Ticket ticket : tickets) {

                ticket.setBooking(null);
                ticket.setStatus(statusDao.getEntityById(1));
                ticketDao.save(ticket);
            }
        }
        }catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete Expired Bookings", e);
            throw new ServiceException("DaoException in AdminLogic. Can't delete Expired Bookings", e);
        }
        return expiredBookingList.size();

    }

    public boolean deleteTicketFromBooking(int bookingId, int ticketId, int langId) throws ServiceException {

        IBookingDao bookingDao = daoFactory.getBookingDao();
        ITicketDao ticketDao = daoFactory.getTicketDao();
        IStatusDao statusDao = daoFactory.getStatusDao();

        boolean flag = false;
        try{
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
        }catch (DaoException e) {
            log.error("DaoException in AdminLogic. Can't delete ticket from booking", e);
            throw new ServiceException("DaoException in AdminLogic. Can't delete ticket from booking", e);
        }
        return flag;
    }

    public boolean editTicketsPriceForPerformance(
            List<TicketsPrice> ticketsPrices) throws ServiceException {
        boolean flag = false;
        ITicketsPriceDao ticketsPriceDao = daoFactory.getTicketsPriceDao();

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
