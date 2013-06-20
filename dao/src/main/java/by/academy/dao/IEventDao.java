package by.academy.dao;

import by.academy.dao.exception.DaoException;
import by.academy.domain.Event;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IEventDao extends IGenericDao<Event, Integer>  {
    List<Event> getEventsInDateInterval(Calendar begin, Calendar end) throws DaoException;

}
