package by.academy.web.commands;

import by.academy.web.wrapper.IWrapper;

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

    public HelloCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }

    public String execute() throws ServletException, IOException {
        return "index.jsp";
    }
}
