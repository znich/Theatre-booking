package by.academy.web.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.Model.CategoryData;
import by.academy.Model.EventData;
import by.academy.Model.PerformanceData;
import by.academy.logic111.SiteLogic;
import by.academy.util.PathProperties;

public class ShowEventsCommand implements ICommand {
	private HttpServletRequest request;
    private HttpServletResponse response;
    private SiteLogic siteLogic = new SiteLogic();
    private List<String> rights = new ArrayList<String>();
    private final String EVENTS_LIST_ATTRIBUTE = "events";
    private final String LOCALE_ID_ATTRIBUTE = "langId";
    private final String CATEGORY_ID="categoryId";
    private final String DATE_INTERVAL="dateInteval";
    private final String CATEGORIES_LIST_ATTRIBUTE= "categories";

	

	public ShowEventsCommand(HttpServletRequest request, HttpServletResponse response) {
		 this.request = request;
	     this.response = response;
	}

	@Override
	public String execute() throws ServletException, IOException {
		
		 int langId = (Integer)request.getSession().getAttribute(LOCALE_ID_ATTRIBUTE);
		 
		 Date date1 = null;
		 Date date2 = null;
		 String dateFirst;
		 String dateLast;
		 String format = "MM/dd/yyyy";		
		 String dateInterval="";
		 int sutki= 86400000;
		 
		if( request.getParameter(DATE_INTERVAL) != null){
			
			dateInterval=request.getParameter(DATE_INTERVAL);
			
			request.getSession().setAttribute(DATE_INTERVAL, dateInterval);
		}
		
		dateInterval = (String) request.getSession().getAttribute(DATE_INTERVAL);
		 if (dateInterval!=null && dateInterval.length()>0){
			 String[] dates = dateInterval.split(" - ");
			 dateFirst = dates[0];
			 dateLast = dates[1];
		 
			 try {
				 date1 = new SimpleDateFormat(format).parse(dateFirst);
				 date2 = new SimpleDateFormat(format).parse(dateLast);
			 } catch (ParseException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }	
		 
		 } else {
			 	Calendar cal = new GregorianCalendar();
			 	cal.setTime(new Date());
			 	cal.set(Calendar.DATE, 1);			 
			 	date1 = cal.getTime();
			 	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			 	cal.setTimeInMillis(cal.getTimeInMillis()-sutki);
			 	date2 = cal.getTime();
		 }
		 
		 List<EventData> eventList=siteLogic.getEventsInDateInterval(date1, date2, langId);		
		// List<EventData> eventList = siteLogic.getAllEvents(langId);
		 CategoryData category = getCategory(langId);
		 				 
		 if (category!=null && category.getId()!=0){
			 
			 List<EventData> sortedEventList = siteLogic.sortEventsByCategory(eventList, category);
			 eventList = sortedEventList ;
		 }
		 
	      request.setAttribute(EVENTS_LIST_ATTRIBUTE, eventList);
	      
	      List<CategoryData> categories = siteLogic.getAllCategories(langId);
	      
	      request.getSession().setAttribute(CATEGORIES_LIST_ATTRIBUTE, categories);

	        return PathProperties.createPathProperties().getProperty(PathProperties.EVENTS_LIST_PAGE);
		
	}
	@Override
	public List<String> getRights() {
		// TODO Auto-generated method stub
		return rights;
	}

	private CategoryData getCategory(int langId){
		
		int categoryId;
		
		
		 if( request.getParameter(CATEGORY_ID) != null){
				
			 categoryId= Integer.parseInt(request.getParameter(CATEGORY_ID));
				
			request.getSession().setAttribute(CATEGORY_ID,  categoryId);
			}		 
		 	
		 if (request.getSession().getAttribute(CATEGORY_ID)==null){
			 categoryId=0;
		 }
		 else {
			 categoryId= (Integer) request.getSession().getAttribute(CATEGORY_ID);
		 }
		
		 CategoryData category = siteLogic.getCategoryById(categoryId, langId);
		 
		return category;
	}
}
