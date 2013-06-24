package by.academy.web.commands;

import by.academy.domain.User;
import by.academy.exception.ServiceException;
import by.academy.logic.RegistratorLogic;
import by.academy.utils.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import MessagesProperties;
//import PathProperties;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistratorCommand implements ICommand {
    private static Log log = LogFactory.getLog(RegistratorCommand.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RegistratorLogic registratorLogic;

    public RegistratorCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }

    public String execute() throws ServletException, IOException, ServiceException {
        String url = PathProperties.createPathProperties().getProperty(PathProperties.REGISTRATION_PAGE);
        String firstName = request.getParameter(SessionConstants.FIRST_NAME_ATTRIBUTE.getName());
        String secondName = request.getParameter(SessionConstants.SECOND_NAME_ATTRIBUTE.getName());
        String email = request.getParameter(RequestConstants.EMAIL_ATTRIBUTE.getName());
        String password = request.getParameter(RequestConstants.PASSWORD_ATTRIBUTE.getName());
        String city = request.getParameter(SessionConstants.CITY_ATTRIBUTE.getName());
        String phone = request.getParameter(SessionConstants.PHONE_ATTRIBUTE.getName());

        try {
            registratorLogic = new RegistratorLogic();
        } catch (ServiceException e) {
            log.error("Can't create registration Logic", e);
            throw new ServiceException("Can't create registration Logic", e);
        }
        // для использования других языков
        String locale = (String) request.getSession().getAttribute("lang");
            if (locale == null) {
                locale = request.getLocale().getLanguage();
        }

        // связь с логикой
        String message = null;
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
            User user = new User();
            user.setEmail(email);
            email = null;
            user.setPassword(password);
            password=null;
            /*user.setName(firstName);
            firstName = null;
            user.setSurname(secondName);
            secondName = null;
            user.setCity(city);
            city = null;
            user.setPhoneNumber(phone);*/
            phone = null;
            user = registratorLogic.registerUser(user);
            url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.REGISTER_SUCCESSFUL, locale);
        }

        request.setAttribute(SessionConstants.FIRST_NAME_ATTRIBUTE.getName(), firstName);
        request.setAttribute(SessionConstants.SECOND_NAME_ATTRIBUTE.getName(), secondName);
        request.setAttribute(RequestConstants.EMAIL_ATTRIBUTE.getName(), email);
        request.setAttribute(RequestConstants.PASSWORD_ATTRIBUTE.getName(), password);
        request.setAttribute(SessionConstants.CITY_ATTRIBUTE.getName(), city);
        request.setAttribute(SessionConstants.PHONE_ATTRIBUTE.getName(), phone);
        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        return url;
    }

}
