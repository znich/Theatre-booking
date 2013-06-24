package by.academy.web;

import by.academy.exception.ServiceException;
import by.academy.web.commands.CommandFactory;
import by.academy.web.commands.ICommand;
import by.academy.web.wrapper.HttpRequestResponseWrapper;
import by.academy.web.wrapper.IWrapper;
import by.academy.web.wrapper.WrapperException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет, выполняющий роль контроллера, который по запросу получает комманду
 * и отсылает её на выполнение.
 */

public class Controller extends HttpServlet {
    private static final long serialVersionUID = -787399415436802178L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IWrapper wrapper = null;
        try
        {
            wrapper = new HttpRequestResponseWrapper(request, response);
        }
        catch (WrapperException e)
        {
            throw new ServletException("Null in request/response in Controller. Can't create HttpRequestResponseWrapper", e);
        }
        ICommand command = CommandFactory.createCommand(wrapper);
        String url = null;
        try {
            url = command.execute();
        } catch (ServiceException e) {
            throw new ServletException();
        }
        if (url != null) {
            wrapper.getRequest().getRequestDispatcher(url).forward(wrapper.getRequest(), wrapper.getResponse());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

}
