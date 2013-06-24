package by.academy.web.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.domain.Category;
import by.academy.domain.TicketsPrice;
import by.academy.exception.ServiceException;
import by.academy.logic.SiteLogic;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShowAddPerformanceCommand implements ICommand {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private static Log log = LogFactory.getLog(ShowAddPerformanceCommand.class);

    public ShowAddPerformanceCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
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
        HttpSession session = request.getSession();
        String inputLangId = request.getParameter(SessionConstants.INPUT_LANG_ID.getName());
        Integer langId = null;
        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }

        if (langId == null) {

            langId = 1;
        }

        List<Category> categoryList = null;
        try {
            categoryList = siteLogic.getAllCategories(langId);
        } catch (ServiceException e) {
            log.error("Can't can't get categories", e);
            throw new ServiceException("Can't can't get categories", e);
        }

        List<TicketsPrice> ticketsPrices = new ArrayList<TicketsPrice>();

        for (int i=1;i<=5;i++){
            TicketsPrice ticketsPrice = new TicketsPrice();
            ticketsPrice.setPriceCategory(i);
            ticketsPrices.add(ticketsPrice);
        }

        request.setAttribute(SessionConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), categoryList);

        session.setAttribute(SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName(), ticketsPrices);
        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.PERFORMANCE_ANSWER_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.LEGEND_ATTRIBUTE.getName(), SessionConstants.LEGEND.getName());

        return PathProperties.createPathProperties().getProperty(
                PathProperties.ADMIN_PAGE);
    }

}