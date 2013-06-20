package by.academy.dao.impl;

import by.academy.dao.IBookingDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.Booking;
import by.academy.domain.User;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class BookingDaoImpl extends GenericDaoImpl<Booking, Integer> implements IBookingDao{


    @Override
    public List<Booking> getBookingByUser(User user) throws DaoException {
        return findByCriteria(Restrictions.eq("user", user));
    }

    @Override
    public List<Booking> getExpiredBooking(Calendar currentDate) throws DaoException {
        return findByCriteria(Restrictions.lt("expDate", currentDate));
    }


}
