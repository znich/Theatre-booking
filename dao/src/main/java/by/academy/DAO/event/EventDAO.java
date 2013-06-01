package by.academy.DAO.event;

import by.academy.Model.EventData;

import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public interface EventDAO {
    public EventData getEventById (int id, int langId) ;
    public List<EventData> getEventsByStartDate (Date date, int langId) ;
    public List <EventData> getAllEvents(int langId);
    public List <EventData> getEventsInDateInterval (long begin, long end, int langId);
    public int getEventsMaxPrice (int id);
    public int getEventsMinPrice (int id);
    public int addEvent(EventData event) ;
    public int editEvent(EventData event) ;
    public int deleteEvent (int id) ;
    public void closeConnection();

}
