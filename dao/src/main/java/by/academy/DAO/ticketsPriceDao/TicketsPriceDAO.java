package by.academy.DAO.ticketsPriceDao;

import java.util.List;

import by.academy.Model.PerformanceData;
import by.academy.Model.TicketsPriceData;

public interface TicketsPriceDAO {

	
	
	public List <TicketsPriceData> getTicketsPriceForPerformance (PerformanceData performance);
	public int addTicketsPrice(TicketsPriceData ticketsPrice);
	int editTicketsPrices(List<TicketsPriceData> ticketPrices);
}
