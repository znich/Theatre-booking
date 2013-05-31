package by.academy.DAO.status;

import by.academy.Model.StatusData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 17.05.13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEStatusDAO implements StatusDAO {
    private Connection connection = null;

    public ORACLEStatusDAO(Connection connection) {
        this.connection = connection;
    }

    public StatusData createStatus(ResultSet rs) throws SQLException {
        StatusData status = new StatusData();

        status.setId(rs.getInt("STATUS_ID"));
        status.setValue(rs.getString("STATUS_VALUE"));

      return status;
    }

	@Override
	public StatusData getStatusById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
