package by.academy.web;

import by.academy.exception.ServiceException;
import by.academy.web.commands.CommandFactory;
import by.academy.web.commands.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 11:35
 * Сервлет, выполняющий роль контроллера, который по запросу получает комманду
 * и отсылает её на выполнение.
 */

public class Controller extends HttpServlet {
    private static final long serialVersionUID = -787399415436802178L;




    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ICommand command = CommandFactory.createCommand(request, response);
        String url = null;
        try {
            url = command.execute();
        } catch (ServiceException e) {
            throw new ServletException();
        }
        if (url != null) {
            request.getRequestDispatcher(url).forward(request, response);
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
