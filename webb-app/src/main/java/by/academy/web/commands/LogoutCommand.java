package by.academy.web.commands;

import by.academy.web.util.SessionConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
public class LogoutCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public LogoutCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String execute() throws ServletException, IOException {
        Object localeObj = request.getSession().getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
        request.getSession().invalidate();
        if (localeObj != null) {
            request.getSession().setAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName(), localeObj);
        }
        return "./";
    }

}
