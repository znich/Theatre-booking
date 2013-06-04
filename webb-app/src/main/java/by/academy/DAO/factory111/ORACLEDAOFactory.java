package by.academy.DAO.factory111;

import by.academy.DAO.booking.BookingDAO;
import by.academy.DAO.booking.ORACLEBookingDAO;
import by.academy.DAO.category.CategoryDAO;
import by.academy.DAO.category.ORACLECategoryDAO;
import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.DAO.event.EventDAO;
import by.academy.DAO.event.ORACLEEventDAO;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory.DAOFactory;
import by.academy.DAO.factory.ORACLEDAOFactory;
import by.academy.DAO.payment.ORACLEPaymentDAO;
import by.academy.DAO.payment.PaymentDAO;
import by.academy.DAO.performance.ORACLEPerformanceDAO;
import by.academy.DAO.performance.PerformanceDAO;
import by.academy.DAO.ticket.ORACLETicketDAO;
import by.academy.DAO.ticket.TicketDAO;
import by.academy.DAO.user.ORACLEUserDAO;
import by.academy.DAO.user.UserDAO;
import by.academy.DAO.seat.ORACLESeatDAO;
import by.academy.DAO.seat.SeatDAO;
import by.academy.DAO.status.ORACLEStatusDAO;
import by.academy.DAO.status.StatusDAO;


import java.sql.Connection;

/**
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 12:59
 * Oracle concrete by.academy.DAO Factory implementation
 */
public class ORACLEDAOFactory extends DAOFactory {
    private static ConnectionPool pool = null;

    public void setConnectionPool(ConnectionPool pool){
        ORACLEDAOFactory.pool = pool;
    }

    // method to create Cloudscape connections
    private static synchronized Connection createConnection(){
        Connection con = null;
        try{
            con = pool.takeConnection();
        } finally{
            System.out.println("connection suceflully getted or not...");
        }
        return con;
    }

    @Override
    public SeatDAO getSeatDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLESeatDAO(con);
    }

    @Override
    public BookingDAO getBookingDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLEBookingDAO(con);
    }

    @Override
    public EventDAO getEventDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLEEventDAO(con);
    }

    @Override
    public PaymentDAO getPaymentDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLEPaymentDAO(con);
    }

    @Override
    public PerformanceDAO getPerformanceDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLEPerformanceDAO(con);
    }

    @Override
    public TicketDAO getTicketDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLETicketDAO(con);
    }

    @Override
    public UserDAO getUserDAO() throws CannotTakeConnectionException {
        Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLEUserDAO(con);
    }

	@Override
	public StatusDAO getStatusDAO() throws CannotTakeConnectionException {
		Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLEStatusDAO(con);
	}

	@Override
	public CategoryDAO getCategoryDAO() throws CannotTakeConnectionException {
		Connection con = createConnection();
        if (con == null){
            throw new CannotTakeConnectionException();
        }
        return new ORACLECategoryDAO(con);
	}
}
