package by.academy.DAO.ticket;

import by.academy.Model.EventData;
import by.academy.Model.SeatData;
import by.academy.Model.TicketData;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public interface TicketDAO {
    public TicketData getTicketById (int id, int langId);
    public List<TicketData> getTicketsByEventId (int id, int langId);
    public List <TicketData> getEventTicketsByStatus (int eventId, int status, int langId);
    public List <TicketData> getTicketsByBookingId (int id, int langId);
    public int editTicket (TicketData ticket);
    public int deleteTicket(int id) throws SQLException;
    public int [] addAllTicketsToEvent(EventData event, List<SeatData> seatsList);
}
