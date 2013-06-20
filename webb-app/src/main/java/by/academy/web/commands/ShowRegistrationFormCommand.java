package by.academy.web.commands;

import by.academy.web.util.PathProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 24.05.13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class ShowRegistrationFormCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ShowRegistrationFormCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws ServletException, IOException {
        return PathProperties.createPathProperties().getProperty(PathProperties.REGISTRATION_PAGE);
    }
}
