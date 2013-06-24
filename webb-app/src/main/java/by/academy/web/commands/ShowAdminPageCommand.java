package by.academy.web.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;

public class ShowAdminPageCommand implements ICommand {

    private HttpServletRequest request;
    private HttpServletResponse response;
    public ShowAdminPageCommand(IWrapper wrapper) {
        this.request = wrapper.getRequest();
        this.response = wrapper.getResponse();
    }

    @Override
    public String execute() throws ServletException, IOException {

        request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(), SessionConstants.ADMIN_ATTRIBUTE.getName());
        request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(), SessionConstants.ADMIN_ANSWER_ATTRIBUTE.getName());

        return PathProperties.createPathProperties().getProperty(
                PathProperties.ADMIN_PAGE);
    }

}