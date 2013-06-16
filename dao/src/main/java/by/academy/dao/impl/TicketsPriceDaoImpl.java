package by.academy.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import by.academy.dao.ITicketsPriceDao;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketsPriceDaoImpl extends GenericDaoImpl<TicketsPrice, Integer> implements ITicketsPriceDao{

	@Override
	public List<TicketsPrice> getTicketsPriceForPerformance(
			Performance performance) {
		 return findByCriteria(Restrictions.eq("perfId", performance.getId()));
		
	}
/*
	@Override
	public List<TicketsPrice> getFreeTickets(Event event) {
		 return findByCriteria(Restrictions.eq("perfId", event.getPerformance().getId(),Restrictions.);
		return null;
	}

	@Override
	public List<TicketsPrice> getMaxFreeTicketPrice(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TicketsPrice> getMinFreeTicketPrice(Event event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	*/
}
