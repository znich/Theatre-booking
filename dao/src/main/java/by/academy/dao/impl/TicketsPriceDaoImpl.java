package by.academy.dao.impl;

import java.util.List;

import by.academy.dao.exception.DaoException;
import org.hibernate.criterion.Restrictions;

import by.academy.dao.ITicketsPriceDao;
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
			Performance performance) throws DaoException {
		 return findByCriteria(Restrictions.eq("perfId", performance.getId()));
		
	}
	
	
}
