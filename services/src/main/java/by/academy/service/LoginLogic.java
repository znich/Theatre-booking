package by.academy.service;

import by.academy.dao.IGenericDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.User;
import by.academy.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class LoginLogic {
    private static Log log = LogFactory.getLog(LoginLogic.class);
    private IGenericDao<User, Integer> userDao;

    public User logination(String email, String password) throws ServiceException {
        User user;
        try {
            Criterion userLoginCondition =
                    Restrictions.conjunction().add(Restrictions.eq("email", email))
                            .add(Restrictions.eq("password", password));
            user = userDao.findOneByCriteria(userLoginCondition);
        } catch (DaoException e) {
            log.error("DaoException in LoginLogic. Can't getUser by email and password", e);
            throw new ServiceException("DaoException in LoginLogic. Can't getUser by email and password", e);
        }

        return user;
    }

    public boolean checkPassword(String password) {
        boolean flag = false;

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{3,10}");
        Matcher matcher = pattern.matcher(password);
        flag = matcher.matches();
        return flag;
    }

    public boolean checkEmail(String email) {
        boolean flag = false;

        Pattern pattern = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        Matcher matcher = pattern.matcher(email);
        flag = matcher.matches();
        return flag;
    }

}
