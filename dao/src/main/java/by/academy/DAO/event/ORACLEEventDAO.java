package by.academy.DAO.event;

import by.academy.DAO.performance.ORACLEPerformanceDAO;
import by.academy.DAO.ticket.ORACLETicketDAO;
import by.academy.Model.EventData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEEventDAO implements EventDAO {
    private Connection connection = null;

    public ORACLEEventDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public EventData getEventById(int id, int langId)  {

        String eventByIdQuery = "SELECT * FROM EVENT INNER JOIN PERFORMANCES ON EVENT.EVENT_NAME = PERFORMANCES.PERFORMANCE_ID " +
                "INNER JOIN PERFORMANCES_PROPERTIES ON PERFORMANCES.PERFORMANCE_ID = PERFORMANCES_PROPERTIES.PERFORMANCE_ID " +
                "INNER JOIN CATEGORY ON PERFORMANCES.CATEGORY_ID = CATEGORY.PARENT_ID AND CATEGORY.LANG_ID = ? " +
                "INNER JOIN PROPERTIES ON PERFORMANCES_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID AND PROPERTIES.LANG_ID = ? WHERE EVENT.EVENT_ID = ?";
        EventData event = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            ps = connection.prepareStatement(eventByIdQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, langId);
            ps.setInt(2, langId);
            ps.setInt(3, id);

            rs = ps.executeQuery();
            if (rs.next()){
                event = createEvent(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(rs, ps, null);
        }

        return event;
    }

    @Override
    public List<EventData> getEventsByStartDate(Date date, int langId) {
        ArrayList<EventData> eventList = new ArrayList<EventData>();
        EventData event = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long dateInMillis = date.getTime();
        String eventsByStartDateQuery = "SELECT * FROM EVENT INNER JOIN PERFORMANCES ON EVENT.EVENT_NAME = PERFORMANCES.PERFORMANCE_ID " +
                "INNER JOIN PERFORMANCES_PROPERTIES ON PERFORMANCES.PERFORMANCE_ID = PERFORMANCES_PROPERTIES.PERFORMANCE_ID " +
                "INNER JOIN CATEGORY ON PERFORMANCES.CATEGORY_ID = CATEGORY.PARENT_ID AND CATEGORY.LANG_ID = ? " +
                "INNER JOIN PROPERTIES ON PERFORMANCES_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID AND PROPERTIES.LANG_ID = ? WHERE EVENT.START_DATE=?";

        try {
            ps = connection.prepareStatement(eventsByStartDateQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, langId);
            ps.setInt(2, langId);
            ps.setLong(3, dateInMillis);

            rs = ps.executeQuery();
            if (rs.next()){
                event = createEvent(rs);
                eventList.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(rs, ps, null);
        }

        return eventList;
    }

    @Override
    public List<EventData> getAllEvents(int langId)  {
        ArrayList<EventData> eventList = new ArrayList<EventData>();
        EventData event = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String allEventsQuery = "SELECT * FROM EVENT INNER JOIN PERFORMANCES ON EVENT.EVENT_NAME = PERFORMANCES.PERFORMANCE_ID " +
                "INNER JOIN PERFORMANCES_PROPERTIES ON PERFORMANCES.PERFORMANCE_ID = PERFORMANCES_PROPERTIES.PERFORMANCE_ID " +
                "INNER JOIN CATEGORY ON PERFORMANCES.CATEGORY_ID = CATEGORY.PARENT_ID AND CATEGORY.LANG_ID = ? " +
                "INNER JOIN PROPERTIES ON PERFORMANCES_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID AND PROPERTIES.LANG_ID = ?";

        try {
            ps = connection.prepareStatement(allEventsQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, langId);
            ps.setInt(2, langId);

            rs = ps.executeQuery();
            if (rs.next()){
                event = createEvent(rs);
                eventList.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(rs, ps, null);
        }

        return eventList;
    }


    @Override
    public List<EventData> getEventsInDateInterval(long begin, long end, int langId)  {

        String eventsInDateIntervalQuery = "SELECT * FROM EVENT INNER JOIN PERFORMANCES ON EVENT.EVENT_NAME = PERFORMANCES.PERFORMANCE_ID " +
                "INNER JOIN PERFORMANCES_PROPERTIES ON PERFORMANCES.PERFORMANCE_ID = PERFORMANCES_PROPERTIES.PERFORMANCE_ID " +
                "INNER JOIN CATEGORY ON PERFORMANCES.CATEGORY_ID = CATEGORY.PARENT_ID AND CATEGORY.LANG_ID = ? " +
                "INNER JOIN PROPERTIES ON PERFORMANCES_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID AND PROPERTIES.LANG_ID = ? WHERE   EVENT_START_TIME > ? AND EVENT_START_TIME < ? " +
                "ORDER BY EVENT_START_TIME";
        ArrayList<EventData> eventList = new ArrayList<EventData>();
        EventData event = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(eventsInDateIntervalQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, langId);
            ps.setInt(2, langId);
            ps.setLong(3, begin);
            ps.setLong(4, end);


            rs = ps.executeQuery();
            if (rs.next()){
                event = createEvent(rs);
                eventList.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(rs, ps, null);
        }

        return eventList;
    }

    @Override
    public int getEventsMinPrice (int id){
        String priceQuery ="SELECT  MIN(PRICE) FROM tickets_price, EVENT, seats, ALL_EVENT_TICKETS, PERFORMANCES "+
                "WHERE TICKETS_PRICE.PERFORMANCE_ID=PERFORMANCES.PERFORMANCE_ID AND EVENT.EVENT_NAME=PERFORMANCES.PERFORMANCE_ID AND "+
                "EVENT.EVENT_ID=ALL_EVENT_TICKETS.TICKET_EVENT_ID "+
                "AND ALL_EVENT_TICKETS.TICKET_PLACE=SEATS.ID AND "+
                "tickets_price.PRICE_CATEGORY=SEATS.PRICE_CATEGORY AND ALL_EVENT_TICKETS.STATUS_ID=1 AND" +
                " EVENT.EVENT_ID="+id;

        Statement statement = null;
        ResultSet rs = null;
        int minPrice = 0;

        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(priceQuery);
            if (rs.next()){
                minPrice=rs.getInt(1);}
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{
            closeAll(rs, null, statement);
        }

        return minPrice;

    }
    @Override
    public int getEventsMaxPrice (int id){
        String priceQuery ="SELECT  MAX(PRICE) FROM tickets_price, EVENT, seats, ALL_EVENT_TICKETS, PERFORMANCES "+
                "WHERE TICKETS_PRICE.PERFORMANCE_ID=PERFORMANCES.PERFORMANCE_ID AND EVENT.EVENT_NAME=PERFORMANCES.PERFORMANCE_ID AND "+
                "EVENT.EVENT_ID=ALL_EVENT_TICKETS.TICKET_EVENT_ID "+
                "AND ALL_EVENT_TICKETS.TICKET_PLACE=SEATS.ID AND "+
                "tickets_price.PRICE_CATEGORY=SEATS.PRICE_CATEGORY AND ALL_EVENT_TICKETS.STATUS_ID=1" +
                " AND  EVENT.EVENT_ID="+id;

        Statement statement = null;
        ResultSet rs = null;
        int minPrice = 0;
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(priceQuery);
            if (rs.next()){
                minPrice=rs.getInt(1);}
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            closeAll(rs, null, statement);
        }

        return minPrice;

    }
    @Override
    public int addEvent(EventData event)  {

        String query = "INSERT INTO EVENTS  (EVENT_NAME, EVENT_START_TIME, EVENT_END_TIME) VALUES (?, ?, ?)";

        PreparedStatement pStatement = null;
        int row = 0;
        try {
            pStatement = connection.prepareStatement(query, new String [] {"EVENT_ID"});

            pStatement.setInt(1, event.getPerformance().getId());
            pStatement.setLong(2, event.getStartTime().getTime());
            pStatement.setLong(3, event.getEndTime().getTime());

            row = pStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            closeAll(null, pStatement, null);
        }

        return row;
    }

    @Override
    public int editEvent(EventData event)  {

        String query = "UPDATE EVENT SET EVENT_NAME= ? , EVENT_START_TIME= ? " +
                "EVENT_END_TIME = ? WHERE EVENT_ID = ?";
        int row=0;
        PreparedStatement pStatement;
        try {
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, event.getPerformance().getId());
            pStatement.setLong(2, event.getStartTime().getTime());
            pStatement.setLong(3, event.getEndTime().getTime());
            pStatement.setInt(4, event.getId());

            row = pStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return row;
    }

    @Override
    public int deleteEvent(int id)  {

        String query = "DELETE FROM EVENT WHERE EVENT_ID = "+id;
        int row=0;
        Statement statement;
        try {
            statement = connection.createStatement();
            row = 	statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return row;
    }

    private EventData createEvent (ResultSet rs) throws SQLException {
        EventData event = new EventData();
        ORACLEPerformanceDAO performanceDAO = new ORACLEPerformanceDAO(connection);
        ORACLETicketDAO ticketDAO = new ORACLETicketDAO(connection);

        event.setId(rs.getInt("EVENT_ID"));


        long beginDate = rs.getLong("EVENT_START_TIME");
        long endDate = rs.getLong("EVENT_END_TIME");

        event.setStartTime(new Date(beginDate));
        event.setEndTime(new Date(endDate));
        event.setMinPrice(getEventsMinPrice(event.getId()));
        event.setMaxPrice(getEventsMaxPrice(event.getId()));

        int freeTickets = ticketDAO.getEventTicketsByStatus(event.getId(), 1, 1).size();

        event.setPerformance(performanceDAO.createPerformance(rs));

        event.setFreeTicketsCount(freeTickets);

        return event;
    }

    private void closeAll(ResultSet rs, PreparedStatement ps, Statement st) {
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
        if (st != null) {
            try {
                st.close();
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
