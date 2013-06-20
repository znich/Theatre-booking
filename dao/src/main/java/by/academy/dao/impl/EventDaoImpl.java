package by.academy.dao.impl;

import by.academy.dao.IEventDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.Event;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventDaoImpl extends GenericDaoImpl<Event, Integer> implements IEventDao {

    @Override
    public List<Event> getEventsInDateInterval(Calendar begin, Calendar end) throws DaoException {
        return findByCriteria( Restrictions.between("startTime", begin, end) );
    }



}
