package by.academy.web.commands;

import by.academy.exception.ServiceException;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 19.05.13
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public interface ICommand {
    public String execute() throws ServletException, IOException, ServiceException;
}
