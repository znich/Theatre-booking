package by.academy.dao.impl;

import by.academy.dao.IEventDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.Event;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 */
public class EventDaoImpl extends GenericDaoImpl<Event, Integer> implements IEventDao {

    @Override
    public List<Event> getEventsInDateInterval(long begin, long end) throws DaoException {
        return findByCriteria(Restrictions.between("startTime", begin, end));
    }



}
