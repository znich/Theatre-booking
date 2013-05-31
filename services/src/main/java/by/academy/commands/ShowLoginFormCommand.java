package by.academy.commands;

import by.academy.util.PathProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public ShowLoginFormCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() throws ServletException, IOException {
        return PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
    }
}
