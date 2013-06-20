package by.academy.dao;

import by.academy.dao.impl.BookingDaoImpl;
import by.academy.dao.impl.GenericDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/8/13
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DaoFactory {
    private static Log log = LogFactory.getLog(DaoFactory.class);

    public static DaoFactory instance(Class factory) {

        try {
            return (DaoFactory)factory.newInstance();
        } catch (Exception ex) {
            log.error("Couldn't create DAOFactory: " + factory);
            throw new RuntimeException();
        }
    }

    public abstract IBookingDao getBookingDao();
    public abstract ICategoryDao getCategoryDao();
    public abstract IEventDao getEventDao();
    public abstract IPaymentMethodDao getPaymentMethodDao();
    public abstract IPerformanceDao getPerformanceDao();
    public abstract IPropertyDao getPropertyDao();
    public abstract ISeatDao getSeatDao();
    public abstract IStatusDao getStatusDao();
    public abstract ITicketDao getTicketDao();
    public abstract ITicketsPriceDao getTicketsPriceDao();
    public abstract IUserDao getUserDao();

}
