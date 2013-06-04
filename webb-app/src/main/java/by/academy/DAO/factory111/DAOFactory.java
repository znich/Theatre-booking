package by.academy.DAO.factory111;

import by.academy.DAO.booking.BookingDAO;
import by.academy.DAO.category.CategoryDAO;
import by.academy.DAO.event.EventDAO;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory.DAOFactory;
import by.academy.DAO.factory.ORACLEDAOFactory;
import by.academy.DAO.payment.PaymentDAO;
import by.academy.DAO.performance.PerformanceDAO;
import by.academy.DAO.seat.SeatDAO;
import by.academy.DAO.status.StatusDAO;
import by.academy.DAO.ticket.TicketDAO;
import by.academy.DAO.user.UserDAO;
import by.academy.Model.CategoryData;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class DAOFactory {

    // List of by.academy.DAO types supported by the factory
    public static final int ORACLE = 1;


    // There will be a method for each by.academy.DAO that can be
    // created. The concrete factories will have to
    // implement these methods.
    public abstract BookingDAO getBookingDAO() throws CannotTakeConnectionException;
    public abstract EventDAO getEventDAO() throws CannotTakeConnectionException;
    public abstract PaymentDAO getPaymentDAO() throws CannotTakeConnectionException;
    public abstract PerformanceDAO getPerformanceDAO() throws CannotTakeConnectionException;
    public abstract TicketDAO getTicketDAO() throws CannotTakeConnectionException;
    public abstract UserDAO getUserDAO() throws CannotTakeConnectionException;
    public abstract SeatDAO getSeatDAO() throws CannotTakeConnectionException;
    public abstract StatusDAO getStatusDAO() throws CannotTakeConnectionException;
    public abstract CategoryDAO getCategoryDAO() throws CannotTakeConnectionException;

    public static DAOFactory getDAOFactory(int whichFactory){
        switch(whichFactory){
            case ORACLE :
                return new ORACLEDAOFactory();
            default:
                return null;
        }
    }
}
