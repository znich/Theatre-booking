package by.academy.dao.impl;

import by.academy.dao.ITicketDao;
import by.academy.domain.Booking;
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
    public List<Ticket> getTicketsByBookingId(Booking booking) {
        return findByCriteria( Restrictions.eq("booking", booking) );
    }
}
