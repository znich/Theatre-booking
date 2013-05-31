package by.academy.web.commands;

import by.academy.Model.CategoryData;
import by.academy.Model.EventData;
import by.academy.Model.PerformanceData;
import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class ShowPerformancesCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SiteLogic siteLogic = new SiteLogic();
    private List<String> rights = new ArrayList<String>();
    private final String PERFORMANCE_LIST_ATTRIBUTE = "performances";
    private final String LOCALE_ID_ATTRIBUTE = "langId";
    private final String CATEGORY_ID="categoryId";
    private final String CATEGORIES_LIST_ATTRIBUTE= "categories";

    public ShowPerformancesCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

    }
    public String execute() throws ServletException, IOException {
    	
        int langId = (Integer)request.getSession().getAttribute(LOCALE_ID_ATTRIBUTE);
      
        
        List<PerformanceData> perfList = siteLogic.getAllPerformances(langId);
        
        CategoryData category = getCategory(langId);
        System.out.println("from session-"+category.toString());
        
        if (category!=null && category.getId()!=0){
        	
        	 System.out.println("11111111111111111111111");
        	 
			 List<PerformanceData> sortedPerfList = siteLogic.sortPerformancesByCategory(perfList, category);
			
			 
			 perfList = sortedPerfList ;
		 }

        request.setAttribute(PERFORMANCE_LIST_ATTRIBUTE, perfList);
        
        List<CategoryData> categories = siteLogic.getAllCategories(langId);
	      
	    request.getSession().setAttribute(CATEGORIES_LIST_ATTRIBUTE, categories);

        return PathProperties.createPathProperties().getProperty(PathProperties.PERFORMANCE_LIST_PAGE);
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

    public List<String> getRights() {
        return rights;
    }
}
