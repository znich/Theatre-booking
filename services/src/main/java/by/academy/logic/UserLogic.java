package by.academy.logic;

import by.academy.dao.booking.BookingDAO;
import by.academy.dao.exception.CannotTakeConnectionException;
import by.academy.dao.factory.DAOFactory;
import by.academy.dao.payment.PaymentDAO;
import by.academy.dao.ticket.TicketDAO;
import by.academy.dao.user.UserDAO;
import by.academy.Model.BookingData;
import by.academy.Model.PaymentData;
import by.academy.Model.TicketData;
import by.academy.Model.UserData;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 19:35
 * Класс, описывающий логику поведения юзера.
 */
public class UserLogic {
    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.dao Factory
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
