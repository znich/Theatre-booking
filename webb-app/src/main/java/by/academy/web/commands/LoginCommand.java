package by.academy.web.commands;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.academy.domain.User;
import by.academy.domain.UserRole;
import by.academy.exception.ServiceException;
import by.academy.logic.LoginLogic;
import by.academy.utils.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 */
public class LoginCommand implements ICommand {
    private static Log log = LogFactory.getLog(LoginCommand.class);
    private HttpServletRequest request;
    private HttpSession session;



    public LoginCommand(IWrapper wrapper) {

        this.request = wrapper.getRequest();
        this.session =  wrapper.getSession();
    }

    public String execute() throws ServletException, IOException, ServiceException {

        String url;
        String locale = (String) session.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        session.removeAttribute(SessionConstants.USER_ATTRIBUTE.getName());
        String email = request.getParameter(RequestConstants.EMAIL_ATTRIBUTE.getName());
        String password = request.getParameter(RequestConstants.PASSWORD_ATTRIBUTE.getName());
        String message = null;

        try {
            LoginLogic loginLogic = new LoginLogic();
            User user = loginLogic.logination(email, password);
            if(user != null){
                if(UserRole.ADMIN.equals(user.getRole())){
                    url = PathProperties.createPathProperties().getProperty(PathProperties.ADMIN_PAGE);
                }else{
                url = PathProperties.createPathProperties().getProperty(PathProperties.PROFILE_USER_PAGE);
                }
                session.setAttribute(SessionConstants.USER_ATTRIBUTE.getName(), user);
            } else{
                message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.ERROR_LOGIN, locale);
                url = PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
            }
        } catch (ServiceException e) {
            log.error("Can't create login Logic", e);
            throw new ServiceException("Can't create login Logic", e);
        }

        request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(), message);
        return url;

    }

}