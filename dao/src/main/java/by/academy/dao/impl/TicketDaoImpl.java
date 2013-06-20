package by.academy.dao.impl;

import by.academy.dao.ITicketDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.Booking;
import by.academy.domain.Event;
import by.academy.domain.Status;
import by.academy.domain.Ticket;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketDaoImpl extends GenericDaoImpl<Ticket, Integer> implements ITicketDao {

    @Override
    public List<Ticket> getTicketsByBookingId(Booking booking) throws DaoException {
        return findByCriteria( Restrictions.eq("booking", booking) );
    }

    @Override
    public List<Ticket> getTicketsByEvent(Event event) throws DaoException {
        return findByCriteria( Restrictions.eq("event", event) );

    }

    @Override
    public List<Ticket> getTicketsByStatus(Event event, Status status) throws DaoException {
        return findByCriteria( Restrictions.eq("event", event), Restrictions.eq("status", status) );

    }
}
