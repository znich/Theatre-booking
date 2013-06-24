package by.academy.web.commands;

import by.academy.web.util.PathProperties;
import by.academy.web.wrapper.IWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 24.05.13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class ShowLoginFormCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ShowLoginFormCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }

    @Override
    public String execute() throws ServletException, IOException {
        return PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
    }
}
