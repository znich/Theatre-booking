package by.academy.web.commands;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.domain.Admin;
import by.academy.domain.User;
import by.academy.exception.ServiceException;
import by.academy.logic.LoginLogic;
import by.academy.web.util.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 19.05.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class LoginCommand implements ICommand {
    private static Log log = LogFactory.getLog(LoginCommand.class);
    private HttpServletRequest request;
    private HttpServletResponse response;



    public LoginCommand(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;
        this.response = response;
    }

    public String execute() throws ServletException, IOException, ServiceException {


        LoginLogic loginLogic = null;
        try {
            loginLogic = new LoginLogic();
        } catch (ServiceException e) {
            log.error("Can't create login Logic", e);
            throw new ServiceException("Can't create login Logic", e);
        }
        HttpSession session = request.getSession();
        User user;
        String url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
        String locale = (String) session.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        if (locale == null) {
            locale = request.getLocale().getLanguage();
        }
        String email = request.getParameter(SessionConstants.EMAIL_ATTRIBUTE.getName());
        String password = request.getParameter(SessionConstants.PASSWORD_ATTRIBUTE.getName());
        String message = null;

        if (!loginLogic.checkEmail(email)) {
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_EMAIL, locale);
            request.setAttribute(SessionConstants.EMAIL_ATTRIBUTE.getName(), email);
        } else if (!loginLogic.checkPassword(password)) {
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_PASS, locale);
            request.setAttribute(SessionConstants.PASSWORD_ATTRIBUTE.getName(), password);
        } else {
            user = loginLogic.logination(email, password);
            if (user == null) {
                message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.ERROR_LOGIN, locale);
                url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
            } else if (loginLogic.isAdmin(user) != null) {
                Admin admin = loginLogic.isAdmin(user);
                message = "";
                url = PathProperties.createPathProperties().getProperty(PathProperties.PROFILE_ADMIN_PAGE);
                session.setAttribute(SessionConstants.ADMIN_ATTRIBUTE.getName(), admin.getId());
            } else {
                message = "";
                url = PathProperties.createPathProperties().getProperty(PathProperties.PROFILE_USER_PAGE);
                session.setAttribute(SessionConstants.USER_ATTRIBUTE.getName(), user.getId());
            }
        }
        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        return url;
    }

}