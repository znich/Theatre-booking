package by.academy.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.academy.logic.SiteLogic;
import by.academy.util.PathProperties;

public class ShowAdminPageCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String ADMIN_ATTRIBUTE = "main";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String ADMIN_ANSWER_ATTRIBUTE = "adminPage";
	public ShowAdminPageCommand(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public String execute() throws ServletException, IOException {
		
		request.setAttribute(MENU_ITEM_ATTRIBUTE, ADMIN_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, ADMIN_ANSWER_ATTRIBUTE);
		
		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}

}
