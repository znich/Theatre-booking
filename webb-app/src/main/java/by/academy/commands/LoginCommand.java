package by.academy.commands;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.Model.AdminData;
import by.academy.Model.UserData;
import by.academy.logic.LoginLogic;
import by.academy.util.MessagesProperties;
import by.academy.util.PathProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 19.05.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class LoginCommand implements ICommand {
    private final String LOCALE_ATTRIBUTE = "lang";
    private final String EMAIL_ATTRIBUTE = "inputEmail";
    private final String PASSWORD_ATTRIBUTE = "inputPassword";
    private final String MESSAGE_ATTRIBUTE = "message";
    private final String ADMIN_ATTRIBUTE = "admin";
    private final String USER_ATTRIBUTE = "user";
    private final String FIRST_NAME_ATTRIBUTE = "firstName";
    private final String SECOND_NAME_ATTRIBUTE = "secondName";
    private HttpServletRequest request;
    private HttpServletResponse response;
    private LoginLogic loginLogic = new LoginLogic();


    public LoginCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String execute() throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserData user;
        String url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
        String locale = (String) session.getAttribute(LOCALE_ATTRIBUTE);
        if (locale == null) {
            locale = request.getLocale().getLanguage();
        }
        String email = request.getParameter(EMAIL_ATTRIBUTE);
        String password = request.getParameter(PASSWORD_ATTRIBUTE);
        String message = null;

        if (!loginLogic.checkEmail(email)) {
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_EMAIL, locale);
            request.setAttribute(EMAIL_ATTRIBUTE, email);
        } else if (!loginLogic.checkPassword(password)) {
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_PASS, locale);
            request.setAttribute(PASSWORD_ATTRIBUTE, password);
        } else {
            user = loginLogic.logination(email, password);
            if (user == null) {
                message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.ERROR_LOGIN, locale);
                url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
            } else if (loginLogic.isAdmin(user) != null) {
                AdminData admin = loginLogic.isAdmin(user);
                message = "";
                url = PathProperties.createPathProperties().getProperty(PathProperties.PROFILE_ADMIN_PAGE);
                session.setAttribute(ADMIN_ATTRIBUTE, admin.getAdminId());
            } else {
                message = "";
                url = PathProperties.createPathProperties().getProperty(PathProperties.PROFILE_USER_PAGE);
                session.setAttribute(USER_ATTRIBUTE, user.getId());
                session.setAttribute(FIRST_NAME_ATTRIBUTE, user.getName());
                session.setAttribute(SECOND_NAME_ATTRIBUTE, user.getSurname());
            }
        }
        request.setAttribute(MESSAGE_ATTRIBUTE, message);
        return url;
    }

}