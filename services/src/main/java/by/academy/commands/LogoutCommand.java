package by.academy.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private final String LOCALE_ATTRIBUTE = "lang";

    public LogoutCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String execute() throws ServletException, IOException {
        Object localeObj = request.getSession().getAttribute(LOCALE_ATTRIBUTE);
        request.getSession().invalidate();
        if (localeObj != null) {
            request.getSession().setAttribute(LOCALE_ATTRIBUTE, localeObj);
        }
        return "./";
    }

}
