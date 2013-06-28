package by.academy.web.commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.academy.domain.*;
import by.academy.exception.ServiceException;
import by.academy.logic.AdminLogic;
import by.academy.logic.SiteLogic;
import by.academy.utils.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EditPerformanceCommand implements ICommand {

    private static Log log = LogFactory.getLog(EditPerformanceCommand.class);
    private HttpServletRequest request;
    private SiteLogic siteLogic;
    private AdminLogic adminLogic;
    private HttpSession session;

    private final String DATE_FORMAT = "MM/dd/yyyy";


    public EditPerformanceCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.session = wrapper.getSession();
    }

    @Override
    public String execute() throws ServletException, IOException, ServiceException {
        try {
            siteLogic = new SiteLogic();
            adminLogic = new AdminLogic();
        } catch (ServiceException e) {
            log.error("Can't create logic class instance", e);
            throw new ServiceException("Can't create logic class instance", e);
        }
        String inputLangId = request.getParameter(SessionConstants.INPUT_LANG_ID.getName());

        Integer langId = null;

        if (inputLangId != null) {

            langId = Integer.parseInt(inputLangId);
        }
        if (langId == null) {

            langId = 1;
        }

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        String dateInterval = request.getParameter(SessionConstants.INPUT_DATE_INTERVAL.getName());

        if (dateInterval != null && dateInterval.length() > 0) {
            String[] dates = dateInterval.split(" - ");

            try {
                date1.setTime(new SimpleDateFormat(DATE_FORMAT).parse( dates[0]));
                date2.setTime(new SimpleDateFormat(DATE_FORMAT).parse(dates[1]));
            } catch (ParseException e) {
                log.error("Wrong date format error", e);
                throw new ServiceException("Wrong date format error", e);
            }
        }

        Integer performanceId = null;
        String idOfPerformance = request.getParameter(SessionConstants.PERFORMANCE_ID_ATTRIBUTE.getName());
        if (idOfPerformance.length() > 0) {
            performanceId = Integer.parseInt(idOfPerformance);
        }
        Integer categoryId = Integer.parseInt(request.getParameter(SessionConstants.INPUT_CATEGORY_ATTRIBUTE.getName()));
        String name = request.getParameter(SessionConstants.INPUT_NAME_ATTRIBUTE.getName());
        String shortDescription = request.getParameter(SessionConstants.INPUT_SHORTDESCRIPTION_ATTRIBUTE.getName());
        String description = request.getParameter(SessionConstants.INPUT_DESCRIPTION_ATTRIBUTE.getName());
        String image = request.getParameter(SessionConstants.INPUT_IMAGE_ATTRIBUTE.getName());

        List<TicketsPrice> ticketsPrices;
        ticketsPrices = (List<TicketsPrice>) session.getAttribute(SessionConstants.TICKETS_PRICE_ATTRIBUTE.getName());

        System.out.println(ticketsPrices.size());

        for (TicketsPrice ticketsPrice : ticketsPrices) {
            ticketsPrice.setPrice(Integer.parseInt(request.getParameter(SessionConstants.INPUT_TICKETS_PRICE_ATTRIBUTE.getName()
                            + ticketsPrice.getPriceCategory())));
        }

        Category category = siteLogic.getCategoryById(categoryId);

        boolean flag = adminLogic.saveOrUpdatePerformance(performanceId, name,
                shortDescription, description, date1, date2, image, category,
                langId);
        String message = null;

        if (flag) {
            flag = adminLogic.editTicketsPriceForPerformance(ticketsPrices);
        }
        if (flag) {
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.REGISTER_SUCCESSFUL, "ru");
        }

        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.PERFORMANCES_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.EDIT_PERF_ANSWER_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);

        return PathProperties.createPathProperties().getProperty(PathProperties.ADMIN_PAGE);
    }
}
