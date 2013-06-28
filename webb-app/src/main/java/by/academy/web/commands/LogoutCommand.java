package by.academy.web.commands;

import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 */
public class LogoutCommand implements ICommand {
    private HttpSession session;

    public LogoutCommand(IWrapper wrapper) {
        this.session = wrapper.getSession();
    }

    public String execute() throws ServletException, IOException {
        session.removeAttribute(SessionConstants.USER_ATTRIBUTE.getName());
        return PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
    }

}
