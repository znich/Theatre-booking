package by.academy.DAO.seat;

import by.academy.Model.SeatData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 17.05.13
 * Time: 0:29
 * To change this template use File | Settings | File Templates.
 */
public interface SeatDAO {
    public List<SeatData> getAllSeats();
    public SeatData createSeat(ResultSet rs) throws SQLException;
    public void closeConnection();
}
