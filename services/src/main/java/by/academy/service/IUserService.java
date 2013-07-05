package by.academy.service;

import by.academy.domain.User;
import by.academy.exception.ServiceException;

import java.util.Calendar;

/**
 */
public interface IUserService {
    boolean bookTicket(Calendar expDate, Calendar madeDate, User user, int ticketCount, int paymentMethodId, int ticketId, int langId) throws ServiceException;
    User logination(User userForVerify) throws ServiceException;
    User registerUser(String firstName, String secondName, String email, String password, String address, String phone, Integer langId) throws ServiceException;

}
