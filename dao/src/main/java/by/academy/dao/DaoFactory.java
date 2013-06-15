package by.academy.dao;

import by.academy.dao.impl.BookingDaoImpl;
import by.academy.dao.impl.GenericDaoImpl;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/8/13
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DaoFactory {
    public static final Class HIBERNATE = by.academy.dao.HibernateDaoFactory.class;

    public static DaoFactory instance(Class factory) {
        try {
            return (DaoFactory)factory.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create DAOFactory: " + factory);
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
