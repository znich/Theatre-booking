package by.academy.DAO.factory;

import by.academy.DAO.booking.BookingDAO;
import by.academy.DAO.booking.ORACLEBookingDAO;
import by.academy.DAO.category.CategoryDAO;
import by.academy.DAO.category.ORACLECategoryDAO;
import by.academy.DAO.event.EventDAO;
import by.academy.DAO.event.ORACLEEventDAO;
import by.academy.DAO.exception.CannotTakeConnectionException;
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
import by.academy.DAO.util.ConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 12:59
 * Oracle concrete by.academy.DAO Factory implementation
 */
public class ORACLEDAOFactory extends DAOFactory {
    private static DataSource pool = null;
    private static Connection con;
    private static ORACLEDAOFactory oraDaoFactory;

    private ORACLEDAOFactory() {
    }

    static public ORACLEDAOFactory getOraDaoFactory() {
        if (oraDaoFactory == null) {
            oraDaoFactory = new ORACLEDAOFactory();
            pool = ConnectionPool.getConnectionPool();
            try {
                con = pool.getConnection();
            } catch (SQLException e) {
            }
        }
        return oraDaoFactory;
    }

            @Override
            public SeatDAO getSeatDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLESeatDAO(con);
            }

            @Override
            public BookingDAO getBookingDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLEBookingDAO(con);
            }

            @Override
            public EventDAO getEventDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLEEventDAO(con);
            }

            @Override
            public PaymentDAO getPaymentDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLEPaymentDAO(con);
            }

            @Override
            public PerformanceDAO getPerformanceDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLEPerformanceDAO(con);
            }

            @Override
            public TicketDAO getTicketDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLETicketDAO(con);
            }

            @Override
            public UserDAO getUserDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLEUserDAO(con);
            }

            @Override
            public StatusDAO getStatusDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLEStatusDAO(con);
            }

            @Override
            public CategoryDAO getCategoryDAO() throws CannotTakeConnectionException {
                if (con == null){
                    throw new CannotTakeConnectionException();
                }
                return new ORACLECategoryDAO(con);
            }
        }
