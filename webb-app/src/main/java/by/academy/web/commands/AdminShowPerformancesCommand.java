package by.academy.web.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdminShowPerformancesCommand implements ICommand {
    private static Log log = LogFactory.getLog(AdminShowPerformancesCommand.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SiteLogic siteLogic;
    private HttpSession session = null;

    public AdminShowPerformancesCommand(HttpServletRequest request,
                                        HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    //
    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }
        session = request.getSession();

        int langId = (Integer) session.getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());

        try {
            request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteLogic.getAllPerformances(langId));
        } catch (ServiceException e) {
            log.error("Can't get performances", e);
            throw new ServiceException("Can't get performances", e);
        }
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.PERFORMANCE_ANSWER_ATTRIBUTE.getName());

        return PathProperties.createPathProperties().getProperty(
                PathProperties.ADMIN_PAGE);
    }

}