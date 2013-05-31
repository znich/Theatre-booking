package by.academy.web.commands;

import by.academy.logic.RegistratorLogic;
import by.academy.Model.UserData;
import by.academy.util.MessagesProperties;
import by.academy.util.PathProperties;
//import MessagesProperties;
//import PathProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistratorCommand implements ICommand {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RegistratorLogic registratorLogic = new RegistratorLogic();
    private List<String> rights = new ArrayList<String>();
    private final String MESSAGE_ATTRIBUTE = "message";

    public RegistratorCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
 
    public String execute() throws ServletException, IOException {
        String url = PathProperties.createPathProperties().getProperty(PathProperties.REGISTRATION_PAGE);
        String firstName = request.getParameter("inputName");
        String secondName = request.getParameter("inputSurname");
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");
        String city = request.getParameter("inputCity");
        String phone = request.getParameter("inputPhone");

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
            UserData user = new UserData();
            user.setEmail(email);
            email = null;
            user.setPassword(password);
            password=null;
            user.setName(firstName);
            firstName = null;
            user.setSurname(secondName);
            secondName = null;
            user.setCity(city);
            city = null;
            user.setPhoneNumber(phone);
            phone = null;
            user = registratorLogic.registerUser(user);
            url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
            message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.REGISTER_SUCCESSFUL, locale);
        }

        request.setAttribute("inputName", firstName);
        request.setAttribute("inputSurname", secondName);
        request.setAttribute("inputEmail", email);
        request.setAttribute("inputPassword", password);
        request.setAttribute("inputAddress", city);
        request.setAttribute("inputPhone", phone);
        request.setAttribute(MESSAGE_ATTRIBUTE, message);
        return url;
    }

    public List<String> getRights() {
        return rights;
    }
}
