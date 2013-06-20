package by.academy.logic;

import by.academy.dao.IUserDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.User;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 11:33
 * Класс, описывающий логику поведения при регистарции пользователя.
 */
public class RegistratorLogic extends DataAccessService {
    private static Log log = LogFactory.getLog(RegistratorLogic.class);
    IUserDao userDao = daoFactory.getUserDao();

    public RegistratorLogic() throws ServiceException {
        super();
    }

    public User registerUser(User user) throws ServiceException {

        try {
            userDao.save(user);
        } catch (DaoException e) {
            log.error("DaoException in RegistratorLogic. Can't register user", e);
            throw new ServiceException("DaoException in RegistratorLogic. Can't register user", e);
        }
        return user;
    }

    public boolean isEmailExist(String email) throws ServiceException {


        boolean flag = true;
        try {
            if (userDao.getUserByEmail(email) == null) {
                flag = false;
            }
        } catch (DaoException e) {
            log.error("DaoException in RegistratorLogic. Can't get user by email", e);
            throw new ServiceException("DaoException in RegistratorLogic. Can't get user by email", e);
        }
        return flag;
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

    public boolean checkFirstName(String firstName) {
        boolean flag = false;

        Pattern pattern = Pattern.compile("[a-zа-яA-ZА-Я ]{3,20}");
        Matcher matcher = pattern.matcher(firstName);
        flag = matcher.matches();
        return flag;
    }

    public boolean checkLastName(String lastName) {
        boolean flag = false;

        Pattern pattern = Pattern.compile("[a-zа-яA-ZА-Я ]{3,20}");
        Matcher matcher = pattern.matcher(lastName);
        flag = matcher.matches();
        return flag;
    }
}
