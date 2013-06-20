package by.academy.web.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class HelloCommand implements ICommand {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public HelloCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String execute() throws ServletException, IOException {
        return "index.jsp";
    }
}
