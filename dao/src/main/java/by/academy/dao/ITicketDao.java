package by.academy.dao;

import by.academy.dao.exception.DaoException;
import by.academy.domain.Booking;
import by.academy.domain.Event;
import by.academy.domain.Status;
import by.academy.domain.Ticket;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITicketDao extends IGenericDao<Ticket, Integer> {
    List<Ticket> getTicketsByBookingId(Booking booking)  throws DaoException;
    List<Ticket> getTicketsByEvent(Event event)  throws DaoException;
    List<Ticket> getTicketsByStatus(Event event, Status status)  throws DaoException;
}
