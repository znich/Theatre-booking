package by.academy.dao;

import by.academy.dao.exception.DaoException;
import by.academy.domain.Booking;
import by.academy.domain.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IBookingDao extends IGenericDao<Booking, Integer> {
    List<Booking> getBookingByUser(User user) throws DaoException;
    List<Booking> getExpiredBooking(Calendar currentDate) throws DaoException;
}
