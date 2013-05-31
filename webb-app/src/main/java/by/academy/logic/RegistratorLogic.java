package by.academy.logic;

import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory.DAOFactory;
import by.academy.DAO.factory.ORACLEDAOFactory;
import by.academy.DAO.user.UserDAO;
import by.academy.Model.UserData;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 11:33
 * Класс, описывающий логику поведения при регистарции пользователя.
 */
public class RegistratorLogic {
    ConnectionPool pool = null;
    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.DAO Factory
    UserDAO userDAO = null;

    public RegistratorLogic() {
        
    }

    public UserData registerUser(UserData user) {

        try {
            userDAO = oracleFactory.getUserDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }
        userDAO.addUser(user);
        return user;
    }

    public boolean isEmailExist(String email) {

        try {
            userDAO = oracleFactory.getUserDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        boolean flag = true;
        if (userDAO.getUserByEmail(email) == null) {
            flag = false;
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
