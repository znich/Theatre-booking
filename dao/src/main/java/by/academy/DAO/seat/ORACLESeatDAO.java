package by.academy.DAO.seat;

import by.academy.Model.SeatData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 17.05.13
 * Time: 0:29
 * To change this template use File | Settings | File Templates.
 */
public class ORACLESeatDAO implements SeatDAO {
    private Connection connection = null;

    public ORACLESeatDAO(Connection connection)  {
        this.connection = connection;
    }

    @Override
    public List<SeatData> getAllSeats() {
        String seatQuery = "SELECT * FROM SEATS";
        ArrayList<SeatData> seatsList = new ArrayList <SeatData>();
        SeatData seat = new SeatData();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(seatQuery);

            while (rs.next()){
                createSeat(rs);
                seatsList.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatsList;
    }

    public SeatData createSeat(ResultSet rs) throws SQLException{
        SeatData seat = new SeatData();

        seat.setId(rs.getInt("ID"));
        seat.setSeatNumber(rs.getInt("SEAT_NUMBER"));
        seat.setRow(rs.getInt("ROW_NUMBER"));
        seat.setPriceCategory(rs.getInt("PRICE_CATEGORY"));
        seat.setSector(rs.getInt("SECTOR_ID"));

        return seat;
    }
    
    public void closeConnection () {
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
