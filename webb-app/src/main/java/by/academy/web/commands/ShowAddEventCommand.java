package by.academy.web.commands;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.domain.Performance;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShowAddEventCommand implements ICommand {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private static Log log = LogFactory.getLog(ShowAddEventCommand.class);

    public ShowAddEventCommand(HttpServletRequest request,
                               HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        SiteLogic siteLogic = null;
        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }
        String inputLangId = request.getParameter(SessionConstants.INPUT_LANG_ID.getName());
        Integer langId = null;
        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }

        if (langId == null) {

            langId = 1;
        }

        Set<Performance> performances = siteLogic.getAllPerformances(langId);
        String startTime = "current";
        String endTime = "current";


        request.setAttribute(SessionConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), performances);
        request.setAttribute(SessionConstants.START_TIME_ATTRIBUTE.getName(), startTime);
        request.setAttribute(SessionConstants.END_TIME_ATTRIBUTE.getName(), endTime);
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.EVENTS_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_EVENT_ANSWER_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return PathProperties.createPathProperties().getProperty(
                PathProperties.ADMIN_PAGE);
    }

}
