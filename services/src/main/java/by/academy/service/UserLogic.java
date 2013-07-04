package by.academy.service;

import by.academy.dao.IGenericDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 */
public class UserLogic {
    private static Log log = LogFactory.getLog(UserLogic.class);
    private IGenericDao<Booking, Integer> bookingDao;
    private IGenericDao<PaymentMethod, Integer> paymentDao;
    private IGenericDao<Ticket, Integer> ticketDao;
    public boolean bookTicket(Calendar expDate, Calendar madeDate, User user, int ticketCount, int paymentMethodId, int ticketId, int langId) throws ServiceException {

        boolean flag = false;

        Booking booking = new Booking();
        booking.setExpDate(expDate);
        booking.setMadeDate(madeDate);
        booking.setUser(user);
        try {
            PaymentMethod paymentMethod = paymentDao.getEntityById(paymentMethodId);
            booking.setPaymentMethod(paymentMethod);

            Ticket ticket = ticketDao.getEntityById(ticketId);
            booking.addTicket(ticket);
            if (bookingDao.save(booking) != null) {
                flag = true;
            }
        } catch (DaoException e) {
            log.error("DaoException in UserLogic. Can't add ticket to booking", e);
            throw new ServiceException("DaoException in UserLogic. Can't add ticket to booking", e);
        }
        return flag;
    }
}
