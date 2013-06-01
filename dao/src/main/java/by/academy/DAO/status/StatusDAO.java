package by.academy.DAO.status;

import by.academy.Model.StatusData;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 17.05.13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public interface StatusDAO {
    public StatusData createStatus(ResultSet rs) throws SQLException;
    public StatusData getStatusById ( int id);
    public void closeConnection();
}
