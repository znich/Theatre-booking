package by.academy.logic111;

import by.academy.DAO.booking.BookingDAO;
import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory111.DAOFactory;
import by.academy.DAO.factory111.ORACLEDAOFactory;
import by.academy.DAO.payment.PaymentDAO;
import by.academy.DAO.ticket.TicketDAO;
import by.academy.DAO.user.UserDAO;
import by.academy.Model.BookingData;
import by.academy.Model.PaymentData;
import by.academy.Model.TicketData;
import by.academy.Model.UserData;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 19:35
 * Класс, описывающий логику поведения юзера.
 */
public class UserLogic {
    ConnectionPool pool = null;
    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.DAO Factory
    BookingDAO bookingDAO = null;
    UserDAO userDAO = null;
    PaymentDAO paymentDAO = null;
    TicketDAO ticketDAO;

    public UserLogic() {
        
    }

    public boolean bookTicket(Date forDate, Date madeDate, UserData user, int ticketCount, int paymentMethodId, int ticketId, int langId){
        try {
            bookingDAO = oracleFactory.getBookingDAO();
            userDAO = oracleFactory.getUserDAO();
            paymentDAO = oracleFactory.getPaymentDAO();
            ticketDAO =  oracleFactory.getTicketDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        boolean flag = false;

        BookingData booking = new BookingData();
        booking.setForDate(forDate);
        booking.setMadeDate(madeDate);
        booking.setUser(user);
        booking.setTicketCount(ticketCount);
        PaymentData paymentMethod = paymentDAO.getPaymentMethodById(paymentMethodId);
        booking.setPaymentMethod(paymentMethod);

        int bookingId = bookingDAO.addBooking(booking);
        if(bookingId != 0){
            TicketData ticket = ticketDAO.getTicketById(ticketId, langId);
            ticket.setBooking(bookingId);
            if(ticketDAO.editTicket(ticket) != 0){
                flag = true;
            }
        }
        return flag;
    }
}
