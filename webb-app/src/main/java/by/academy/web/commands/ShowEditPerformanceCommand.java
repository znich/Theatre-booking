package by.academy.web.commands;

import by.academy.domain.Category;
import by.academy.domain.Performance;
import by.academy.domain.TicketsPrice;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/12/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowEditPerformanceCommand implements ICommand {
    private static Log log = LogFactory.getLog(ShowEditPerformanceCommand.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private SiteLogic siteLogic;

    public ShowEditPerformanceCommand(HttpServletRequest request,
                                      HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {

        try {
            siteLogic = new SiteLogic();
        } catch (ServiceException e) {
            log.error("Can't create SiteLogic", e);
            throw new ServiceException("Can't create SiteLogic", e);
        }
        HttpSession session = request.getSession();
        String inputLangId = request.getParameter(SessionConstants.INPUT_LANG_ID.getName());
        Integer langId = null;
        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }

        if (langId == null) {

            langId = 1;
        }

        Integer performanceId = Integer.parseInt(request
                .getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName()));

        Performance performance = null;
        List<Category> categoryList = null;
        List<TicketsPrice> ticketsPrices = null;
        try {
            performance = siteLogic.getPerformancesById(performanceId, langId);
            categoryList = siteLogic.getAllCategories(langId);
            ticketsPrices = siteLogic.getTicketsPriceByPerformance(performance);
        } catch (ServiceException e) {
            log.error("Can't collect entities", e);
            throw new ServiceException("Can't collect entities", e);
        }

        Calendar cal = GregorianCalendar.getInstance();
        Calendar startDate = performance.getStartDate();
        Calendar endDate = performance.getEndDate();
        //cal.setTime(startDate);
        StringBuilder dateIntervalBulder = new StringBuilder();
        dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
        dateIntervalBulder.append(" - ");
        //cal.setTime(endDate);
        dateIntervalBulder.append((cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
        String dateInterval = dateIntervalBulder.toString();


        request.setAttribute(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName(), performance);
        request.setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), categoryList);
        request.setAttribute(SessionConstants.DATE_INTERVAL.getName(), dateInterval);
        session.setAttribute(SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName(), ticketsPrices);
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.PERFORMANCE_ANSWER_ATTRIBUTE.getName());

        return PathProperties.createPathProperties().getProperty(
                PathProperties.ADMIN_PAGE);
    }
}
