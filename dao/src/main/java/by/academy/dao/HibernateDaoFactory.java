package by.academy.dao;

import by.academy.dao.exception.DaoException;
import by.academy.dao.impl.*;
import by.academy.dao.util.HibernateUtil;
import by.academy.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 */
public class HibernateDaoFactory extends DaoFactory {
    private static Log log = LogFactory.getLog(HibernateDaoFactory.class);

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
        Session session = null;
        try {
            session =  HibernateUtil.getSession();
        } catch (DaoException e) {
            log.error("Can not get session");
        }
        return session;
    }

    private GenericDaoImpl instantiateDao(Class daoClass) {
        try {
            GenericDaoImpl dao = (GenericDaoImpl)daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            log.error("Can not instantiate DAO: " + daoClass, ex);
            throw new RuntimeException();
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

    public static class PerformanceDaoImpl
            extends GenericDaoImpl<Performance, Integer>
            implements IPerformanceDao {}

 

}
