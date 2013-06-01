package by.academy.DAO.ticket;

import by.academy.DAO.seat.ORACLESeatDAO;
import by.academy.DAO.status.ORACLEStatusDAO;
import by.academy.Model.EventData;
import by.academy.Model.SeatData;
import by.academy.Model.TicketData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class ORACLETicketDAO implements TicketDAO {

    private Connection connection = null;

    public ORACLETicketDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TicketData getTicketById(int id, int langId) {
        
        String ticketByIdQuery = "SELECT * FROM ALL_EVENT_TICKETS INNER JOIN  SEATS ON ALL_EVENT_TICKETS.TICKET_PLACE=SEATS.ID " +
        		"INNER JOIN EVENT ON ALL_EVENT_TICKETS.TICKET_EVENT_ID=EVENT.EVENT_ID" +        
        		"INNER JOIN TICKET_STATUS ON (ALL_EVENT_TICKETS.STATUS_ID=TICKET_STATUS.PARENT_ID AND TICKET_STATUS.LANG_ID = ?) " +
        		"INNER JOIN TICKETS_PRICE ON EVENT.EVENT_NAME=TICKETS_PRICE.PERFORMANCE_ID WHERE  TICKET_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        TicketData ticket = new TicketData();
        try {
            ps = connection.prepareStatement(ticketByIdQuery);
            ps.setInt(1, langId);
            ps.setInt(2, id);

            rs = ps.executeQuery();
            if (rs.next()){
            ticket = createTicket(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }

        return ticket;
    }
    
    public TicketData getTicketById(int id){
    	 TicketData ticket = getTicketById( id, 1);
    	 return ticket;
    }
    
    @Override
    public List<TicketData> getTicketsByEventId(int id, int langId) {
        String ticketsByEventQuery = "SELECT * FROM ALL_EVENT_TICKETS INNER JOIN  SEATS ON ALL_EVENT_TICKETS.TICKET_PLACE=SEATS.ID " +
        		"INNER JOIN EVENT ON ALL_EVENT_TICKETS.TICKET_EVENT_ID=EVENT.EVENT_ID" +        
        		"INNER JOIN TICKET_STATUS ON (ALL_EVENT_TICKETS.STATUS_ID=TICKET_STATUS.PARENT_ID AND TICKET_STATUS.LANG_ID = ?) " +
        		"INNER JOIN TICKETS_PRICE ON EVENT.EVENT_NAME=TICKETS_PRICE.PERFORMANCE_ID WHERE  TICKET_EVENT_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        TicketData ticket = new TicketData();
        ArrayList<TicketData> ticketsByEventList = new ArrayList<TicketData>();

        try {
            ps = connection.prepareStatement(ticketsByEventQuery);
            ps.setInt(1, langId);
            ps.setInt(2,  id);
            rs = ps.executeQuery();

            while (rs.next()) {
                ticket = createTicket(rs);
                ticketsByEventList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }

        return ticketsByEventList;
    }

    public List<TicketData> getTicketsByEventId(int id){
    	 ArrayList<TicketData> ticketsByEventList  =  (ArrayList<TicketData>) getTicketsByEventId(id,1);
		return ticketsByEventList;
    }
    
    @Override
    public List<TicketData> getEventTicketsByStatus(int eventId, int status, int langId) {
        String ticketsByStatusQuery = "SELECT * FROM ALL_EVENT_TICKETS INNER JOIN  SEATS ON ALL_EVENT_TICKETS.TICKET_PLACE=SEATS.ID " +
                "INNER JOIN EVENT ON ALL_EVENT_TICKETS.TICKET_EVENT_ID=EVENT.EVENT_ID  " +
                "INNER JOIN TICKET_STATUS ON (ALL_EVENT_TICKETS.STATUS_ID=TICKET_STATUS.PARENT_ID AND TICKET_STATUS.LANG_ID = ?) " +
                "INNER JOIN TICKETS_PRICE ON EVENT.EVENT_NAME=TICKETS_PRICE.PERFORMANCE_ID WHERE  TICKET_EVENT_ID = ? AND ALL_EVENT_TICKETS.STATUS_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TicketData> eventTicketsByStatusList = new ArrayList<TicketData>();
        TicketData ticket = new TicketData();

        try {
            ps = connection.prepareStatement(ticketsByStatusQuery);
            ps.setInt(1, langId);
            ps.setInt(2, eventId);
            ps.setInt(3, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                ticket = createTicket(rs);
                eventTicketsByStatusList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }

        return eventTicketsByStatusList;
    }

    
    public List<TicketData> getEventTicketsByStatus(int eventId, int status){
    	ArrayList<TicketData> eventTicketsByStatusList = (ArrayList<TicketData>) getEventTicketsByStatus(eventId, status, 1);
		return eventTicketsByStatusList;
    }
    
    @Override
    public List<TicketData> getTicketsByBookingId(int id, int langId) {
        String ticketsByBookingQuery = "SELECT * FROM ALL_EVENT_TICKETS INNER JOIN  SEATS ON ALL_EVENT_TICKETS.TICKET_PLACE=SEATS.ID " +
        		"INNER JOIN EVENT ON ALL_EVENT_TICKETS.TICKET_EVENT_ID=EVENT.EVENT_ID" +        
        		"INNER JOIN TICKET_STATUS ON (ALL_EVENT_TICKETS.STATUS_ID=TICKET_STATUS.PARENT_ID AND TICKET_STATUS.LANG_ID = ?) " +
        		"INNER JOIN TICKETS_PRICE ON EVENT.EVENT_NAME=TICKETS_PRICE.PERFORMANCE_ID WHERE  ALL_EVENT_TICKETS.BOOKING_ID = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TicketData> ticketsByBookingList = new ArrayList<TicketData>();
        TicketData ticket = new TicketData();
        try {
            ps = connection.prepareStatement(ticketsByBookingQuery);
            ps.setInt(1, langId);
            ps.setInt(2, id);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                ticket = createTicket(rs);
                ticketsByBookingList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }

        return ticketsByBookingList;
    }
    
    public List<TicketData> getTicketsByBookingId(int id){
    	ArrayList<TicketData> ticketsByBookingList =  (ArrayList<TicketData>) getTicketsByBookingId( id, 1);
		return ticketsByBookingList;
    }

    @Override
    public int deleteTicket(int ticketId) throws SQLException {
        String deleteTicketQuery = "DELETE FROM ALL_EVENT_TICKETS WHERE TICKET_ID = " + ticketId;
        Statement statement = connection.createStatement();

        int rows = statement.executeUpdate(deleteTicketQuery);
        return rows;
    }

    @Override
    public int [] addAllTicketsToEvent(EventData event, List<SeatData> seatsList){

        String addTicketQuery = "INSERT INTO ALL_EVENT_TICKETS (TICKET_EVENT_ID, BOOKING_ID, TICKET_PLACE, STATUS_ID) VALUES" +
        		" (?,?,?,?)";
        PreparedStatement ps = null;
        int [] tickets = null;
        try {
            ps = connection.prepareStatement(addTicketQuery);

            for (SeatData seat : seatsList) {
                ps.setInt(1, event.getId());
                ps.setInt(2, 0);
                ps.setInt(3, seat.getId());
                ps.setInt(4, 1);
                ps.addBatch();

            }
         tickets=   ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
    
    @Override
    public int editTicket(TicketData ticket) {
		
		 String editTicketQuery = "UPDATE ALL_EVENT_TICKETS SET TICKET_EVENT_ID = ?, BOOKING_ID = ?, TICKET_PLACE = ?, STATUS_ID = ? " +
		 		"WHERE TICKED_ID = ?";
	        PreparedStatement ps = null;
	        int  row = 0;
	        try {
	            ps = connection.prepareStatement(editTicketQuery);
	            
	                ps.setInt(1, ticket.getEvent());
	                ps.setInt(2, ticket.getBooking());
	                ps.setInt(3, ticket.getPlace().getId());
	                ps.setInt(4, ticket.getStatus().getId());
	                ps.setInt(5, ticket.getId());      
	            
	                row =   ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	        	closeAll(null, ps);
	        }

	        return row;
	}

    public TicketData createTicket(ResultSet rs) throws SQLException {
        TicketData ticket = new TicketData();
        ORACLESeatDAO seatDAO = new ORACLESeatDAO(connection);
        ORACLEStatusDAO statusDAO = new ORACLEStatusDAO(connection);

        ticket.setId(rs.getInt("TICKET_ID"));
        ticket.setEvent(rs.getInt("TICKET_EVENT_ID"));
        ticket.setPrice(rs.getInt("PRICE"));
        ticket.setBooking(rs.getInt("BOOKING_ID"));
        ticket.setStatus(statusDAO.createStatus(rs));
        ticket.setPlace(seatDAO.createSeat(rs));
        return ticket;
    }

    private void closeAll(ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
            }
        }
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
