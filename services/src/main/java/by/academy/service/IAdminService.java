package by.academy.service;

import by.academy.domain.Category;
import by.academy.domain.TicketsPrice;
import by.academy.exception.ServiceException;

import java.util.Calendar;
import java.util.Set;

/**
 */
public interface IAdminService {
    boolean saveOrUpdatePerformance(Integer performanceId, String name, String shortDescription, String description, Calendar date1, Calendar date2, String image, Category category, Integer langId) throws ServiceException;

    boolean editTicketsPriceForPerformance(Set<TicketsPrice> ticketsPrices) throws ServiceException;
}
