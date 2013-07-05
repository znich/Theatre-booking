package by.academy.service.impl;

import by.academy.dao.IGenericDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.*;
import by.academy.exception.ServiceException;
import by.academy.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;

/**
 */
public class UserService implements IUserService {
    private static Log log = LogFactory.getLog(UserService.class);
    private IGenericDao<Booking, Integer> bookingDao;
    private IGenericDao<PaymentMethod, Integer> paymentDao;
    private IGenericDao<Ticket, Integer> ticketDao;
    private IGenericDao<User, Integer> userDao;

    public void setUserDao(IGenericDao<User, Integer> userDao) {
        this.userDao = userDao;
    }

    public void setBookingDao(IGenericDao<Booking, Integer> bookingDao) {
        this.bookingDao = bookingDao;
    }

    public void setPaymentDao(IGenericDao<PaymentMethod, Integer> paymentDao) {
        this.paymentDao = paymentDao;
    }

    public void setTicketDao(IGenericDao<Ticket, Integer> ticketDao) {
        this.ticketDao = ticketDao;
    }

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

    public User logination(User userForVerify) throws ServiceException {
        try {
            Criterion userLoginCondition =
                    Restrictions.conjunction().add(Restrictions.eq("email", userForVerify.getEmail()))
                            .add(Restrictions.eq("password", userForVerify.getPassword()));
            User user = userDao.findOneByCriteria(userLoginCondition);
            return user;
        } catch (DaoException e) {
            log.error("DaoException in LoginLogic. Can't getUser by email and password", e);
            throw new ServiceException("DaoException in LoginLogic. Can't getUser by email and password", e);
        }


    }

    public User registerUser(String firstName, String secondName, String email, String password, String address, String phone, Integer langId) throws ServiceException {

        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            for (PropertyNameEnum e : PropertyNameEnum.values()) {
                Property parentProperty = new Property();
                Property childProperty = new Property();
                childProperty.setLangId(langId);
                childProperty.setRootProperty(parentProperty);

                switch (e) {
                    case FIRST_NAME:
                        parentProperty.setName(e);
                        childProperty.setName(e);
                        childProperty.setValue(firstName);
                        break;
                    case SURNAME:
                        parentProperty.setName(e);
                        childProperty.setName(e);
                        childProperty.setValue(secondName);
                        break;
                    case PHONE_NUMBER:
                        parentProperty.setName(e);
                        childProperty.setName(e);
                        childProperty.setValue(phone);
                        break;
                    case ADDRESS:
                        parentProperty.setName(e);
                        childProperty.setName(e);
                        childProperty.setValue(address);
                        break;
                }

                parentProperty.getChildProperties().add(childProperty);
                if(parentProperty.getName() != null){
                    user.getProperties().add(parentProperty);
                }
            }

            userDao.save(user);
            return user;
        } catch (DaoException e) {
            log.error("DaoException in RegistratorLogic. Can't register user", e);
            throw new ServiceException("DaoException in RegistratorLogic. Can't register user", e);
        }
    }
}
