package by.academy.logic;

import by.academy.DAO.category.CategoryDAO;
import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.DAO.event.EventDAO;
import by.academy.DAO.exception.CannotTakeConnectionException;
import by.academy.DAO.factory.DAOFactory;
import by.academy.DAO.factory.ORACLEDAOFactory;
import by.academy.DAO.performance.PerformanceDAO;
import by.academy.Model.CategoryData;
import by.academy.Model.EventData;
import by.academy.Model.PerformanceData;

import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class SiteLogic {

    ConnectionPool pool = null;
    DAOFactory oracleFactory =
            DAOFactory.getDAOFactory(DAOFactory.ORACLE); // create the required by.academy.DAO Factory
    PerformanceDAO perfDAO = null;
    EventDAO eventDAO = null;
    CategoryDAO categoryDAO=null;

    public SiteLogic() {
        try{
            ConnectionPool.init();
        }catch (SQLException e1) {
            e1.printStackTrace();
        }
        pool = ConnectionPool.getInstance();

        if (oracleFactory instanceof ORACLEDAOFactory){
            ORACLEDAOFactory my = (ORACLEDAOFactory)oracleFactory;
            my.setConnectionPool(pool);
        }
        
        AdminLogic admLogic = new AdminLogic();
        admLogic.deleteExpiredBookings();
    }


    public List<PerformanceData> getAllPerformances(int langId) {

        try {
            perfDAO = oracleFactory.getPerformanceDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        List<PerformanceData> performances = perfDAO.getAllPerformances(langId);
        perfDAO.closeConncetion();
        return performances;
    }
    
public List<PerformanceData> sortPerformancesByCategory(List<PerformanceData> performances, CategoryData category){
    	
    	List<PerformanceData> sortedPerformances = new ArrayList<PerformanceData>() ;
    	
    	for (PerformanceData performance: performances){
    		System.out.println("category sorted");
    		System.out.println(performance.getCategory().toString());
    		if (performance.getCategory().equals(category)){
    			sortedPerformances.add(performance);
    			System.out.println("2222222222222222222222");
    		}
    	}
		return sortedPerformances;
    	
    }


    public List<EventData> getAllEvents(int langId) {

        try {
            eventDAO = oracleFactory.getEventDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }

        List<EventData> events = eventDAO.getAllEvents(langId);
        
        eventDAO.closeConncetion();
      
        return events;
    }

    public List<EventData> getEventsInDateInterval(Date date1, Date date2, int langID) {

        try {
            eventDAO = oracleFactory.getEventDAO();
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        }
        long begin;
        long end;
        if (date1.before(date2)){
            begin = date1.getTime();
            end = date2.getTime();
        }
        else {
            begin = date2.getTime();
            end = date1.getTime();
        }
        List<EventData> events = eventDAO.getEventsInDateInterval(begin, end, langID);
        eventDAO.closeConncetion();
        return events;
    }
    
    public List<EventData> sortEventsByCategory(List<EventData> events, CategoryData category){
    	
    	List<EventData> sortedEvents = new ArrayList<EventData>() ;
    	
    	for (EventData event: events){
    		if (event.getPerformance().getCategory().equals(category)){
    			sortedEvents.add(event);
    		}
    	}
		return sortedEvents;
    	
    }
    
public List<CategoryData> getAllCategories( int lang){
		
    	try {
			categoryDAO = oracleFactory.getCategoryDAO();
		} catch (CannotTakeConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	List<CategoryData> categories = null;
		try {
			categories = categoryDAO.getAllCategories(lang);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		categoryDAO.closeConncetion();
    	return categories;
    	
    }

    public CategoryData getCategoryById(int id, int lang){
		
    	try {
			categoryDAO = oracleFactory.getCategoryDAO();
		} catch (CannotTakeConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	CategoryData category = null;
		try {
			category = categoryDAO.getCategoryById(id, lang);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		categoryDAO.closeConncetion();
    	return category;
    	
    }

}
