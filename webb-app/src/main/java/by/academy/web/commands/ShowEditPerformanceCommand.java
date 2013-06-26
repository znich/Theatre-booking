package by.academy.web.commands;

import by.academy.domain.Category;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 */
public class ShowEditPerformanceCommand implements ICommand {
    private static Log log = LogFactory.getLog(ShowEditPerformanceCommand.class);

    private HttpServletRequest request;
    private HttpSession session;

    public ShowEditPerformanceCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.session = wrapper.getSession();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        SiteLogic siteLogic;
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

        Integer performanceId = Integer.parseInt(request.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName()));

        Performance performance;
        List<Category> categoryList;
        Set<TicketsPrice> ticketsPrices;
        try {
            performance = siteLogic.getPerformancesById(performanceId, langId);
            categoryList = siteLogic.getAllCategories(langId);
            ticketsPrices = performance.getTicketsPrices();
        } catch (ServiceException e) {
            log.error("Can't collect entities", e);
            throw new ServiceException("Can't collect entities", e);
        }

        Calendar cal = GregorianCalendar.getInstance();
        StringBuilder dateIntervalBulder = new StringBuilder();
        dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
        dateIntervalBulder.append(" - ");
        dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
        String dateInterval = dateIntervalBulder.toString();


        request.setAttribute(SessionConstants.PERFORMANCE_ATTRIBUTE.getName(), performance);
        request.setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), categoryList);
        request.setAttribute(RequestConstants.DATE_INTERVAL.getName(), dateInterval);
        session.setAttribute(SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName(), ticketsPrices);
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());

        return PathProperties.createPathProperties().getProperty(PathProperties.ADMIN_PAGE);
    }
}
