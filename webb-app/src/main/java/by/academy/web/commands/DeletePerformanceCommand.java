package by.academy.web.commands;

import by.academy.exception.ServiceException;
import by.academy.logic.AdminLogic;
import by.academy.logic.SiteLogic;
import by.academy.utils.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 */
public class DeletePerformanceCommand implements ICommand {
    private static Log log = LogFactory.getLog(DeletePerformanceCommand.class);
    private HttpServletRequest request;
    private HttpSession session;

    public DeletePerformanceCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.session = wrapper.getSession();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        String idOfPerformance = request.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName());
        String locale = (String) session.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        int langId = (Integer)request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
        String message = null;
        SiteLogic siteLogic = null;
        if (idOfPerformance == null) {
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.PERF_DEL_ERROR, locale);
        } else {
            try {
                Integer performanceId =  Integer.parseInt(idOfPerformance);
                AdminLogic adminLogic = new AdminLogic();
                siteLogic = new SiteLogic();
                if (adminLogic.deletePerformance(performanceId)) {
                    message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.PERF_DEL_SUCCESS, locale);
                }
            } catch (ServiceException e) {
                log.error("Can't create logic class instance", e);
                throw new ServiceException("Can't create logic class instance", e);
            }
        }
        request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteLogic.getAllPerformances(langId));
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_LIST_ANSWER_ATTRIBUTE.getName());
        return PathProperties.createPathProperties().getProperty(PathProperties.ADMIN_PAGE);
    }
}
