package by.academy.web.commands;

import by.academy.exception.ServiceException;
import by.academy.logic.RegistratorLogic;
import by.academy.utils.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistratorCommand implements ICommand {
    private static Log log = LogFactory.getLog(RegistratorCommand.class);
    private HttpServletRequest request;
    private RegistratorLogic registratorLogic;
    private HttpSession session;

    public RegistratorCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.session =  wrapper.getSession();
    }

    public String execute() throws ServletException, IOException, ServiceException {

        String url = PathProperties.createPathProperties().getProperty(PathProperties.REGISTRATION_PAGE);
        String firstName = request.getParameter(RequestConstants.FIRST_NAME_ATTRIBUTE.getName());
        String secondName = request.getParameter(RequestConstants.SECOND_NAME_ATTRIBUTE.getName());
        String email = request.getParameter(RequestConstants.EMAIL_ATTRIBUTE.getName());
        String password = request.getParameter(RequestConstants.PASSWORD_ATTRIBUTE.getName());
        String address = request.getParameter(RequestConstants.CITY_ATTRIBUTE.getName());
        String phone = request.getParameter(RequestConstants.PHONE_ATTRIBUTE.getName());

        try {
            registratorLogic = new RegistratorLogic();
        } catch (ServiceException e) {
            log.error("Can't create registration Logic", e);
            throw new ServiceException("Can't create registration Logic", e);
        }
        String locale = (String) session.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        int langId = (Integer) session.getAttribute(SessionConstants.LOCALE_ID_ATTRIBUTE.getName());

        String message;
        if (!registratorLogic.checkFirstName(firstName)) {
            firstName = null;
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_FIRST_NAME, locale);
        } else if (!registratorLogic.checkLastName(secondName)) {
            secondName = null;
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_SECOND_NAME, locale);
        } else if (!registratorLogic.checkEmail(email)) {
            email = null;
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_EMAIL, locale);
        } else if (!registratorLogic.checkPassword(password)) {
            password = null;
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_PASS, locale);
        } else if (registratorLogic.isEmailExist(email)) {
            email = null;
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.EMAIL_EXIST, locale);
        }else {

            registratorLogic.registerUser(firstName, secondName, email, password, address, phone, langId);
            url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.REGISTER_SUCCESSFUL, locale);
        }

        request.setAttribute(RequestConstants.FIRST_NAME_ATTRIBUTE.getName(), firstName);
        request.setAttribute(RequestConstants.SECOND_NAME_ATTRIBUTE.getName(), secondName);
        request.setAttribute(RequestConstants.EMAIL_ATTRIBUTE.getName(), email);
        request.setAttribute(RequestConstants.PASSWORD_ATTRIBUTE.getName(), password);
        request.setAttribute(RequestConstants.CITY_ATTRIBUTE.getName(), address);
        request.setAttribute(RequestConstants.PHONE_ATTRIBUTE.getName(), phone);
        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        return url;
    }

}
