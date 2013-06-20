package by.academy.web.commands;

import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 */
public class ShowPerformancesCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;

    private static Log log = LogFactory.getLog(ShowPerformancesCommand.class);

    public ShowPerformancesCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

    }

    public String execute() throws ServletException, IOException, ServiceException {
        SiteLogic siteLogic = null;
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Error was thrown in ShowPerformancesCommand: can't create SiteLogic instance", e);
            throw new ServiceException("Error was thrown in ShowPerformancesCommand: can't create SiteLogic instance", e);
        }

        int langId = (Integer)request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
        try {
            request.setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteLogic.getAllCategories(langId));
        } catch (ServiceException e) {
            log.error("Can't get category list", e);
            throw new ServiceException("Can't get category list", e);
        }
        String cat = (String) request.getParameter(SessionConstants.CATEGORY_ID.getName());
        String perfId = (String) request.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName());

        if(cat != null && Integer.parseInt(cat) != 0){
            int selectedCategory = Integer.parseInt(cat);
            request.setAttribute(SessionConstants.CATEGORY_ID.getName(), selectedCategory);
            try {
                request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteLogic.getPerformancesByCategory(selectedCategory));
            } catch (ServiceException e) {
                log.error("Can't get performances by category", e);
                throw new ServiceException("Can't get performances by category", e);
            }
        }else{
            try {
                request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteLogic.getAllPerformances(langId));
            } catch (ServiceException e) {
                log.error("Can't get performances", e);
                throw new ServiceException("Can't get performances", e);
            }
        }

        if(perfId != null){
            try {
                request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteLogic.getPerformancesById(Integer.parseInt(perfId), langId));
            } catch (ServiceException e) {
                log.error("Can't get performance by id", e);
                throw new ServiceException("Can't get performance by id", e);
            }
            return PathProperties.createPathProperties().getProperty(PathProperties.PERFORMANCE_PAGE);
        }
        return PathProperties.createPathProperties().getProperty(PathProperties.PERFORMANCE_LIST_PAGE);
    }
}
