package by.academy.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.academy.dao.IEventDao;
import by.academy.dao.IPerformanceDao;
import by.academy.dao.ISeatDao;
import by.academy.dao.IStatusDao;
import by.academy.dao.ITicketDao;
import by.academy.dao.ITicketsPriceDao;
import by.academy.domain.Booking;
import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.domain.Property;
import by.academy.domain.Seat;
import by.academy.domain.Status;
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
    IPerformanceDao perfDao = daoFactory.getPerformanceDao();
    ITicketDao ticketDao = daoFactory.getTicketDao();
    ITicketsPriceDao ticketsPriceDao = daoFactory.getTicketsPriceDao();
    IEventDao eventDao = daoFactory.getEventDao();
    ISeatDao seatDao = daoFactory.getSeatDao();
    IStatusDao statusDao = daoFactory.getStatusDao();

    

    public AdminLogic() throws ServiceException {
        super();
    }

    public boolean saveOrUpdatePerformance(Integer id, String title, String shortDescription, String description, Calendar startDate, Calendar endDate,
                                  String image, Category category, int langId) {

        boolean flag = false;
        Performance performance = null;
        if(id == null){
            performance = new Performance();
        }else{
            performance = perfDao.getEntityById(id);
        }
        performance.setStartDate(startDate);
        performance.setEndDate(endDate);
        performance.setCategory(category);

        Set<Property> propertyList = new HashSet<Property>();
        Property property = new Property();

        for (PerformancePropertyNames e : PerformancePropertyNames.values()){
            property.setName(e.getId());

            switch (e) {
                case NAME:
                    property.setValue(title);
                    break;
                case SHORT_DESCRIPTION:
                    property.setValue(shortDescription);
                    break;
                case DESCRIPTION:
                    property.setValue(description);
                    break;
                case IMAGE:
                    property.setValue(image);
                    break;
            }

            property.setLangId(langId);
            propertyList.add(property);
        }
        performance.setProperties(propertyList);

        if (perfDao.save(performance) != null) {
            flag = true;
        }
        return flag;
    }

    public boolean deletePerformance(Integer perfId){
        boolean flag = false;
        perfDao.delEntity(perfId);
        return flag;
    }

    public boolean addEvent(int langId, int performance, Calendar startTime, Calendar endTime) {

        boolean flag = false;
        Event event = new Event();
        event.setPerformance(perfDao.getEntityById(performance));
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        List<Seat> seats = seatDao.findAll();
        Ticket ticket = new Ticket();
        Set<Ticket> ticketSet = new HashSet<Ticket>();

        for(Seat s: seats){
            ticket.setPlace(s);
            ticket.setStatus();
        }

        if (eventDao.save(event) != null) {

            if(ticketDao.addAllTicketsToEvent(event, seats) != null){
               flag = true;
            }
        }

        return flag;
    }

    public  int deleteExpiredBookings(){

    	Date currentDate = new Date(System.currentTimeMillis());
    	ArrayList<Integer> delBookingIDs = (ArrayList<Integer>) bookingDAO.deleteExpiredBookings(currentDate);
    	Status status = new Status();
    	if (delBookingIDs!=null){
    		
    		
    		for (Integer bookingId : delBookingIDs ){
    			
    			List <Ticket> tickets = ticketDAO.getTicketsByBookingId(bookingId, 1);
    			
    			for (Ticket ticket : tickets){
    				
    				ticket.setBooking(0);    				    				
    				status.setId(1);
    				ticket.setStatus(status);
    				
    				ticketDAO.editTicket(ticket);
    			}
    		}
    	}
    	
		return delBookingIDs.size();
    	
    }
    public boolean deleteTicketFromBooking(int bookingId, int ticketId, int langId){

        boolean flag = false;
        Booking booking = bookingDAO.getBookingById(bookingId, langId);
        if(booking != null){
            booking.setTicketCount(booking.getTicketCount() - 1);
            bookingDAO.editBooking(booking);
            Status status = new Status();
            Ticket ticket = ticketDAO.getTicketById(ticketId, langId);
            if(ticket != null){
                status.setId(1);
                ticket.setStatus(status);
                ticket.setBooking(0);
                ticketDAO.editTicket(ticket);
                flag = true;
            }
        }
    return flag;
    }

    public boolean editTicketsPriceForPerformance(List<TicketsPrice> ticketsPrices){
        boolean flag = false;
        if (ticketsPriceDao.editTicketsPrices(ticketsPrices)>0){
            flag = true;
        }
        return flag;

    }

}
