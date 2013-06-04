package by.academy.logic111;

import by.academy.DAO.booking.BookingDAO;
import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.DAO.event.EventDAO;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory111.DAOFactory;
import by.academy.DAO.factory111.ORACLEDAOFactory;
import by.academy.DAO.performance.PerformanceDAO;
import by.academy.DAO.seat.SeatDAO;
import by.academy.DAO.status.StatusDAO;
import by.academy.DAO.ticket.TicketDAO;
import by.academy.Model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:26
 * Класс, описывающий логику поведения администратора.
 */
public class AdminLogic {
    ConnectionPool pool = null;
    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.DAO Factory
    PerformanceDAO perfDAO = null;
    EventDAO eventDAO = null;
    TicketDAO ticketDAO = null;
    SeatDAO seatDAO = null;
    BookingDAO bookingDAO = null;
    StatusDAO statusDAO = null;
    

    public AdminLogic() {
        try{
            ConnectionPool.init();
        }catch (SQLException e1) {
            e1.printStackTrace();
        }
        pool = ConnectionPool.getInstance();

        if (oracleFactory instanceof ORACLEDAOFactory){
            ORACLEDAOFactory my = (ORACLEDAOFactory)oracleFactory;
            my.setConnectionPool(pool);
        }
    }

    public boolean addPerformance(String title, String shortDescription, String description, Date startDate, Date endDate,
                                  String image, CategoryData category, int language) {
        try {
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        boolean flag = false;
        PerformanceData performance = new PerformanceData();
        performance.setName(title);
        performance.setShortDescription(shortDescription);
        performance.setDescription(description);
        performance.setStartDate(startDate);
        performance.setEndDate(endDate);
        performance.setImage(image);
        performance.setCategory(category);
        performance.setLanguage(language);

        if (perfDAO.addPerformance(performance) != 0) {
            flag = true;
        }
        perfDAO.closeConncetion();
        return flag;
    }

    public boolean deletePerformance(int perfId){

        try {
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }
        boolean flag = false;
        if (perfDAO.deletePerformance(perfId) == 1) {
            flag = true;
        }
        perfDAO.closeConncetion();
        return flag;
    }

     public boolean editPerformance(String title, String shortDescription, String description, Date startDate, Date endDate,
                                    String image, CategoryData category, int language){
         try {
             perfDAO = oracleFactory.getPerformanceDAO();
         } catch (CannotTakeConnectionException e) {
             e.printStackTrace();
         }
         boolean flag = false;
         PerformanceData performance = new PerformanceData();
         performance.setName(title);
         performance.setShortDescription(shortDescription);
         performance.setDescription(description);
         performance.setStartDate(startDate);
         performance.setEndDate(endDate);
         performance.setImage(image);
         performance.setCategory(category);
         performance.setLanguage(language);

         if (perfDAO.editPerformance(performance) < 0) {
             if(perfDAO.addPerformance(performance) != 0 && perfDAO.editPerformance(performance) > 0){
                 flag = true;
                 return flag;
             }
         }
         if (perfDAO.editPerformance(performance) > 0) {
             flag = true;
         }
         perfDAO.closeConncetion();
         return flag;
     }

    public boolean addEvent(int langId, int performance, long startTime, long endTime) {
        try {
            eventDAO = oracleFactory.getEventDAO();
            ticketDAO = oracleFactory.getTicketDAO();
            seatDAO = oracleFactory.getSeatDAO();
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        boolean flag = false;
        EventData event = new EventData();
        event.setPerformance(perfDAO.getPerformanceById(performance, langId));
        event.setStartTime(new Date(startTime));
        event.setEndTime(new Date(endTime));


        if (eventDAO.addEvent(event) != 0) {
            List<SeatData> seats = seatDAO.getAllSeats();

            if(ticketDAO.addAllTicketsToEvent(event, seats) != null){
               flag = true;
            }
        }
        
        eventDAO.closeConncetion();
        perfDAO.closeConncetion();
        ticketDAO.closeConncetion();
        seatDAO.closeConncetion();
        
        return flag;
    }

    public  int deleteExpiredBookings(){
    	
    	try {
			bookingDAO = oracleFactory.getBookingDAO();
			ticketDAO = oracleFactory.getTicketDAO();	
			
		} catch (CannotTakeConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Date currentDate = new Date(System.currentTimeMillis());
    	ArrayList<Integer> delBookingIDs = (ArrayList<Integer>) bookingDAO.deleteExpiredBookings(currentDate);
    	StatusData status = new StatusData();
    	if (delBookingIDs!=null){
    		
    		
    		for (Integer bookingId : delBookingIDs ){
    			
    			List <TicketData> tickets = ticketDAO.getTicketsByBookingId(bookingId, 1);	
    			
    			for (TicketData ticket : tickets){
    				
    				ticket.setBooking(0);    				    				
    				status.setId(1);
    				ticket.setStatus(status);
    				
    				ticketDAO.editTicket(ticket);
    			}
    		}
    	}
    	bookingDAO.closeConncetion();
    	ticketDAO.closeConncetion();
		return delBookingIDs.size();
    	
    }
    public boolean deleteTicketFromBooking(int bookingId, int ticketId, int langId){
        try {
            perfDAO = oracleFactory.getPerformanceDAO();
            ticketDAO = oracleFactory.getTicketDAO();
            bookingDAO = oracleFactory.getBookingDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }
        boolean flag = false;
        BookingData booking = bookingDAO.getBookingById(bookingId, langId);
        if(booking != null){
            booking.setTicketCount(booking.getTicketCount() - 1);
            bookingDAO.editBooking(booking);
            StatusData status = new StatusData();
            TicketData ticket = ticketDAO.getTicketById(ticketId, langId);
            if(ticket != null){
                status.setId(1);
                ticket.setStatus(status);
                ticket.setBooking(0);
                ticketDAO.editTicket(ticket);
                flag = true;
            }
        }
        perfDAO.closeConncetion();
        ticketDAO.closeConncetion();
        bookingDAO.closeConncetion();
    return flag;
    }

}
