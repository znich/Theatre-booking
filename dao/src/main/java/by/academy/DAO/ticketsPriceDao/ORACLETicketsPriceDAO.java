package by.academy.DAO.ticketsPriceDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.academy.Model.PerformanceData;
import by.academy.Model.TicketsPriceData;

public class ORACLETicketsPriceDAO implements TicketsPriceDAO {

	private Connection connection = null;

	public ORACLETicketsPriceDAO(Connection connection) {
		 this.connection = connection;
	}

	@Override
	public List<TicketsPriceData> getTicketsPriceForPerformance(
			PerformanceData performance) {
		 
		String getPrice = "SELECT * FROM TICKETS_PRICE WHERE PERFORMANCE_ID = ? ORDER BY PRICE_CATEGORY";
		PreparedStatement ps = null;
        ResultSet rs = null;
        TicketsPriceData ticketsPrice = new TicketsPriceData();
        ArrayList<TicketsPriceData> ticketsPriceList = new ArrayList<>();
        
        try {
			ps = connection.prepareStatement(getPrice);
			ps.setInt(1, performance.getId());
			rs = ps.executeQuery();
			
			 while (rs.next()) {
				 ticketsPrice = createTicketsPrice (rs);
				 ticketsPriceList.add(ticketsPrice);
	            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		return  ticketsPriceList;
	}

	

	@Override
	public int addTicketsPrice(TicketsPriceData ticketsPrice) {
		
		String addPriceQuery= "INSERT INTO TICKETS_PRICE (PERFORMANCE_ID, PRICE_CATEGORY, PRICE) VALUES (?,?,?)";
		PreparedStatement ps = null;
		int rows = 0;
				
		try {
			ps = connection.prepareStatement(addPriceQuery);
			ps.setInt(1, ticketsPrice.getPerformanceId());
			ps.setInt(2, ticketsPrice.getPriceCategory());
			ps.setInt(3, ticketsPrice.getPrice());
			rows = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public int editTicketsPrices (List<TicketsPriceData> ticketPrices){
		
		String editTicketPricesQuery = "UPDATE TICKETS_PRICE SET PRICE = ? WHERE (PERFORMANCE_ID = ? AND PRICE_CATEGORY = ?)";
		PreparedStatement ps = null;
		int[] rows = null;
		
		try {
			ps = connection.prepareStatement(editTicketPricesQuery);
			
			for (TicketsPriceData ticketsPrice : ticketPrices){
				ps.setInt(1, ticketsPrice.getPrice());
				ps.setInt(2, ticketsPrice.getPerformanceId());
				ps.setInt(3, ticketsPrice.getPriceCategory());
				ps.addBatch();
			}
			rows = ps.executeBatch();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows.length;
	}
	
	private TicketsPriceData createTicketsPrice (ResultSet rs) throws SQLException {
		
		TicketsPriceData ticketsPrice = new TicketsPriceData();
		
		ticketsPrice.setId(rs.getInt("ID"));
		ticketsPrice.setPerformanceId(rs.getInt("PERFORMANCE_ID"));
		ticketsPrice.setPriceCategory(rs.getInt("PRICE_CATEGORY"));
		ticketsPrice.setPrice(rs.getInt("PRICE"));
		
		return ticketsPrice;
	}
}
