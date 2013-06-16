package by.academy.logic;

import java.util.Calendar;

import java.util.List;


import by.academy.dao.*;
import by.academy.domain.Booking;
import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.domain.Property;
import by.academy.domain.Seat;
import by.academy.domain.Ticket;
import by.academy.domain.TicketsPrice;
import by.academy.domain.property_names.PerformancePropertyNames;
import by.academy.exception.ServiceException;


/**
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:26
 * Класс, описывающий логику поведения администратора.
 */
public class AdminLogic extends DataAccessService {

    public AdminLogic() throws ServiceException {
        super();
    }

    public boolean saveOrUpdatePerformance(Integer id, String title, String shortDescription, String description, Calendar startDate, Calendar endDate,
                                           String image, Category category, int langId) {

        boolean flag = false;
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        Performance performance;

        if (id == null) {
            performance = new Performance();
        } else {
            performance = perfDao.getEntityById(id);
        }
        performance.setStartDate(startDate);
        performance.setEndDate(endDate);
        performance.setCategory(category);

        for (PerformancePropertyNames e : PerformancePropertyNames.values()) {
        	
        	Property parentProperty = new Property();
            Property childProperty = new Property();
        	parentProperty.setName(e.getId());

            childProperty.setName(e.getId());
            childProperty.setLangId(langId);
            childProperty.setRootProperty(parentProperty);
            

            switch (e) {
                case NAME:
                    childProperty.setValue(title);
                    break;
                case SHORT_DESCRIPTION:
                    childProperty.setValue(shortDescription);
                    break;
                case DESCRIPTION:
                    childProperty.setValue(description);
                    break;
                case IMAGE:
                    childProperty.setValue(image);
                    break;
            }

            performance.setProperty(parentProperty);
        }

        if (perfDao.save(performance) != null) {
            flag = true;
        }
        return flag;
    }

    public boolean deletePerformance(Integer perfId) {
        boolean flag = false;
        IPerformanceDao perfDao = daoFactory.getPerformanceDao();

        perfDao.delEntity(perfId);
        return flag;
    }

    public boolean addEvent(int langId, int perfId, Calendar startTime, Calendar endTime) {

        IPerformanceDao perfDao = daoFactory.getPerformanceDao();
        IStatusDao statusDao = daoFactory.getStatusDao();
        ISeatDao seatDao = daoFactory.getSeatDao();
        IEventDao eventDao = daoFactory.getEventDao();

        boolean flag = false;
        Event event = new Event();
        event.setPerformance(perfDao.getEntityById(perfId));
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        List<Seat> seats = seatDao.findAll();
        

        for (Seat s : seats) {
        	Ticket ticket = new Ticket();
            ticket.setPlace(s);
            ticket.setStatus(statusDao.getEntityById(1));
            ticket.setEvent(event);
            event.setTicket(ticket);
        }

        if (eventDao.save(event) != null) {
            flag = true;
        }
        return flag;
    }

    
    
    public int deleteExpiredBookings() {

        ITicketDao ticketDao = daoFactory.getTicketDao();
        IBookingDao bookingDao = daoFactory.getBookingDao();
        IStatusDao statusDao = daoFactory.getStatusDao();
        Calendar currentDate = Calendar.getInstance();

        List<Booking> expiredBookingList;
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

        return expiredBookingList.size();

    }

    public boolean deleteTicketFromBooking(int bookingId, int ticketId, int langId) {

        IBookingDao bookingDao = daoFactory.getBookingDao();
        ITicketDao ticketDao = daoFactory.getTicketDao();
        IStatusDao statusDao = daoFactory.getStatusDao();

        boolean flag = false;
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
        return flag;
    }

    public boolean editTicketsPriceForPerformance(List<TicketsPrice> ticketsPrices) {
        boolean flag = false;
        ITicketsPriceDao ticketsPriceDao = daoFactory.getTicketsPriceDao();

        for (TicketsPrice tp : ticketsPrices) {
            ticketsPriceDao.save(tp);
            flag = true;
        }
        return flag;

    }

}
