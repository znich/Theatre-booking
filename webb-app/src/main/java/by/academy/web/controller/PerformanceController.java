package by.academy.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.academy.exception.ServiceException;
import by.academy.service.ISiteService;
import by.academy.web.exception.UIException;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;

/**
 */

@Controller
@RequestMapping(value = "/performances")
public class PerformanceController {
    private static Logger log = Logger.getLogger(PerformanceController.class);

    @Autowired
    private ISiteService siteService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String showPerformances(Map<String, Object> model, HttpServletRequest request) throws UIException {
        try {
        	Integer langId = (Integer)request.getSession().getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
            model.put(RequestConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteService.getAllPerformances(langId));
            model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteService.getAllCategories(langId));
            return "showPerformances";
        } catch (ServiceException e) {
            log.error("ServiceException.Cannot collect performances");
            throw new UIException(e);
        }

    }


}
