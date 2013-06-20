package by.academy.logic;

import by.academy.dao.IBookingDao;
import by.academy.dao.IPaymentMethodDao;
import by.academy.dao.ITicketDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.Booking;
import by.academy.domain.PaymentMethod;
import by.academy.domain.Ticket;
import by.academy.domain.User;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 19:35
 * Класс, описывающий логику поведения юзера.
 */
public class UserLogic extends DataAccessService {
    private static Log log = LogFactory.getLog(UserLogic.class);
    public UserLogic() throws ServiceException {
        super();
    }

    public boolean bookTicket(Calendar expDate, Calendar madeDate, User user, int ticketCount, int paymentMethodId, int ticketId, int langId) throws ServiceException {

        IBookingDao bookingDao = daoFactory.getBookingDao();
        IPaymentMethodDao paymentDao = daoFactory.getPaymentMethodDao();
        ITicketDao ticketDao = daoFactory.getTicketDao();

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
