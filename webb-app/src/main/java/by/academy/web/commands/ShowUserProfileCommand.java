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
 * Date: 29.05.13
 * Time: 10:59
 * To change this template use File | Settings | File Templates.
 */
public class  ShowUserProfileCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ShowUserProfileCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }
    @Override
    public String execute() throws ServletException, IOException {
        return PathProperties.createPathProperties().getProperty(PathProperties.PROFILE_USER_PAGE);
    }
}
