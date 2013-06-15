package by.academy.commands;

import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private final String PERFORMANCE_LIST_ATTRIBUTE = "performanceList";
    private final String CATEGORY_LIST_ATTRIBUTE = "categories";
    private final String CATEGORY_ID="categoryId";
    private final String PERFORMANCE_ID="perf_id";
    private final String LOCALE_ID_ATTRIBUTE = "langId";

    public ShowPerformancesCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

    }

    public String execute() throws ServletException, IOException {

        int langId = (Integer)request.getSession().getAttribute(LOCALE_ID_ATTRIBUTE);
        request.setAttribute(CATEGORY_LIST_ATTRIBUTE, siteLogic.getAllCategories(langId));
        String cat = (String) request.getParameter(CATEGORY_ID);
        String perfId = (String) request.getParameter(PERFORMANCE_ID);

        if(cat != null && Integer.parseInt(cat) != 0){
            int selectedCategory = Integer.parseInt(cat);
            request.setAttribute(CATEGORY_ID, selectedCategory);
            request.setAttribute(PERFORMANCE_LIST_ATTRIBUTE, siteLogic.getPerformancesByCategory(selectedCategory, langId));
        }else{
            request.setAttribute(PERFORMANCE_LIST_ATTRIBUTE, siteLogic.getAllPerformances(langId));
        }

        if(perfId != null){
            request.setAttribute(PERFORMANCE_LIST_ATTRIBUTE, siteLogic.getPerformancesById(Integer.parseInt(perfId), langId));
            return PathProperties.createPathProperties().getProperty(PathProperties.PERFORMANCE_PAGE);
        }
        return PathProperties.createPathProperties().getProperty(PathProperties.PERFORMANCE_LIST_PAGE);
    }
}
