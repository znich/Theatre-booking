package by.academy.service;

import by.academy.domain.Category;
import by.academy.domain.Event;
import by.academy.domain.Performance;
import by.academy.exception.ServiceException;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 */
public interface ISiteService {
    List<Category> getAllCategories(Integer langId) throws ServiceException;

    Set<Performance> getPerformancesByCategory(Integer selectedCategory, Integer langId) throws ServiceException;

    List<Performance> getAllPerformances(Integer langId) throws ServiceException;

    Performance getPerformancesById(Integer id, Integer langId) throws ServiceException;

    Category getCategoryById(Integer categoryId) throws ServiceException;

    List<Event> getEventsInDateInterval(Calendar date1, Calendar date2, Integer langId) throws ServiceException;

    List<Event> sortEventsByCategory(List<Event> eventList, Category category);

	List<Event> getAllEvents(int langId);

	Event getEventById(Integer eventId, int langId);
}
