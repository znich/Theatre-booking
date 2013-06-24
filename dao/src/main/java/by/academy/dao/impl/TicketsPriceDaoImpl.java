package by.academy.dao.impl;

import java.util.List;

import by.academy.dao.exception.DaoException;
import org.hibernate.criterion.Restrictions;

import by.academy.dao.ITicketsPriceDao;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;

/**
 */
public class TicketsPriceDaoImpl extends GenericDaoImpl<TicketsPrice, Integer> implements ITicketsPriceDao{

	@Override
	public List<TicketsPrice> getTicketsPriceForPerformance(Performance performance) throws DaoException {
		 return findByCriteria(Restrictions.eq("perfId", performance.getId()));
		
	}
	
	
}
