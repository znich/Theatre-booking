package by.academy.web.commands;

import by.academy.web.util.PathProperties;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 */
public class ShowLoginFormCommand implements ICommand {

    @Override
    public String execute() throws ServletException, IOException {
        return PathProperties.createPathProperties().getProperty(PathProperties.LOGIN_PAGE);
    }
}
