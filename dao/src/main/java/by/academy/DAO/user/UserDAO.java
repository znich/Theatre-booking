package by.academy.DAO.user;

import by.academy.Model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {
    public UserData getUserById (int id);
    public List<UserData> getAllUsers();
    public UserData getUserByEmail (String email);
    public UserData getUserByEmailAndPassword(String email, String password);
    public int addUser(UserData user);
    public int editUser(UserData user);
    public int deleteUser (int id);
    public UserData createUser(ResultSet rs) throws SQLException;
    public void closeConnection();

}
