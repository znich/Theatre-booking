package by.academy.logic;

import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory.DAOFactory;
import by.academy.DAO.factory.ORACLEDAOFactory;
import by.academy.DAO.user.UserDAO;
import by.academy.Model.AdminData;
import by.academy.Model.UserData;

import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 12:46
 * Класс, описывающий логику поведения при логинации пользователя.
 */
public class LoginLogic {

    ConnectionPool pool = null;
    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.DAO Factory
    UserDAO userDAO = null;

    public LoginLogic() {
    }


    public UserData logination(String email, String password) {

     
        

        try {
            userDAO = oracleFactory.getUserDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        UserData user = null;
        if (checkPassword(password) && checkEmail(email)) {
            user = userDAO.getUserByEmailAndPassword(email, password);
        }
        return user;
    }

    public AdminData isAdmin(UserData user) {
        AdminData admin = null;
        if (user != null && user.getEmail() != null) {
            ResourceBundle rb = ResourceBundle.getBundle(
                    "properties/admin");
            String adminLogin = rb.getString ("admin.login");

            if(adminLogin == user.getEmail()){
                admin = new AdminData();
            }
        }
        return admin;
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
