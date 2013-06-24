package by.academy.logic;

import by.academy.dao.IUserDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.User;
import by.academy.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 12:46
 * Класс, описывающий логику поведения при логинации пользователя.
 */
public class LoginLogic extends DataAccessService {
    private static Log log = LogFactory.getLog(LoginLogic.class);

    public LoginLogic() throws ServiceException {
        super();

    }

    public User logination(String email, String password) throws ServiceException {
        User user;
        try {
            IUserDao userDao = daoFactory.getUserDao();
            user = userDao.getUserByEmailAndPassword(email, password);
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
