package by.academy.web.controller;

import by.academy.domain.Category;
import by.academy.domain.Performance;
import by.academy.exception.ServiceException;
import by.academy.service.ISiteService;
import by.academy.web.exception.UIException;
import by.academy.web.util.RequestConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 */

@Controller
@RequestMapping(value = "/performances")
public class PerformanceController {
    private static Logger log = Logger.getLogger(PerformanceController.class);

    @Autowired
    private ISiteService siteService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String showPerformances(Map<String, Object> model) throws UIException {
        try {
            Integer langId = 1;
            model.put(RequestConstants.PERFORMANCE_LIST_ATTRIBUTE.getName(), siteService.getAllPerformances(langId));
            model.put(RequestConstants.CATEGORIES_LIST_ATTRIBUTE.getName(), siteService.getAllCategories(langId));
            return "showPerformances";
        } catch (ServiceException e) {
            log.error("ServiceException.Cannot collect performances");
            throw new UIException(e);
        }

    }


}
