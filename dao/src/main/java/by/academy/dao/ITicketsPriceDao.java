package by.academy.dao;

import java.util.List;

import by.academy.dao.IGenericDao;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;

/**
 * Created with IntelliJ IDEA. User: Siarhei Poludvaranin Date: 6/7/13 Time:
 * 12:08 PM To change this template use File | Settings | File Templates.
 */
public interface ITicketsPriceDao extends IGenericDao<TicketsPrice, Integer> {

	List<TicketsPrice> getTicketsPriceForPerformance(Performance performance);

	/*List<TicketsPrice> getFreeTickets(Event event);

	List<TicketsPrice> getMaxFreeTicketPrice(Event event);

	List<TicketsPrice> getMinFreeTicketPrice(Event event);*/
}
