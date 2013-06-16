package by.academy.dao;

import by.academy.dao.impl.*;
import by.academy.dao.util.HibernateUtil;
import by.academy.domain.*;
import org.hibernate.Session;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/8/13
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateDaoFactory extends DaoFactory {

    @Override
    public IBookingDao getBookingDao() {
        return (IBookingDao)instantiateDao(BookingDaoImpl.class);
    }

    @Override
    public ICategoryDao getCategoryDao() {
        return (ICategoryDao)instantiateDao(CategoryDaoImpl.class);
    }

    @Override
    public IEventDao getEventDao() {
        return (IEventDao)instantiateDao(EventDaoImpl.class);
    }

    @Override
    public IPaymentMethodDao getPaymentMethodDao() {
        return (IPaymentMethodDao)instantiateDao(PaymentMethodDaoImpl.class);
    }

    @Override
    public IPerformanceDao getPerformanceDao() {
        return (IPerformanceDao)instantiateDao(PerformanceDaoImpl.class);
    }

    @Override
    public IPropertyDao getPropertyDao() {
        return (IPropertyDao)instantiateDao(PropertyDaoImpl.class);
    }

    @Override
    public ISeatDao getSeatDao() {
        return (ISeatDao)instantiateDao(SeatDaoImpl.class);
    }

    @Override
    public IStatusDao getStatusDao() {
        return (IStatusDao)instantiateDao(StatusDaoImpl.class);
    }

    @Override
    public ITicketDao getTicketDao() {
        return (ITicketDao)instantiateDao(TicketDaoImpl.class);
    }

    @Override
    public ITicketsPriceDao getTicketsPriceDao() {
        return (ITicketsPriceDao)instantiateDao(TicketsPriceDaoImpl.class);
    }

    @Override
    public IUserDao getUserDao() {
        return (IUserDao)instantiateDao(UserDaoImpl.class);
    }
    protected Session getCurrentSession() {
        return HibernateUtil.getSession();
    }

    private GenericDaoImpl instantiateDao(Class daoClass) {
        try {
            GenericDaoImpl dao = (GenericDaoImpl)daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    public static class CategoryDaoImpl
            extends GenericDaoImpl<Category, Integer>
            implements ICategoryDao {}

    public static class PaymentMethodDaoImpl
            extends GenericDaoImpl<PaymentMethod, Integer>
            implements IPaymentMethodDao {}


    public static class PropertyDaoImpl
            extends GenericDaoImpl<Property, Integer>
            implements IPropertyDao {}

    public static class SeatDaoImpl
            extends GenericDaoImpl<Seat, Integer>
            implements ISeatDao {}

    public static class StatusDaoImpl
            extends GenericDaoImpl<Status, Integer>
            implements IStatusDao {}

    public static class TicketsPriceDaoImpl
            extends GenericDaoImpl<TicketsPrice, Integer>
            implements ITicketsPriceDao {}

}
