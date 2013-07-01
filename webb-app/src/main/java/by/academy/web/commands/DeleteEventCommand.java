package by.academy.web.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import by.academy.exception.ServiceException;
import by.academy.logic.AdminLogic;
import by.academy.logic.SiteLogic;
import by.academy.utils.MessagesProperties;
import by.academy.web.util.PathProperties;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;

public class DeleteEventCommand implements ICommand {

	private static Log log = LogFactory.getLog(DeletePerformanceCommand.class);
	private HttpServletRequest request;
	private HttpSession session;

	public DeleteEventCommand(IWrapper wrapper) {
		this.request = wrapper.getRequest();
		this.session = wrapper.getSession();
		log.info("Entering Delete Event Command");
	}

	@Override
	public String execute() throws ServletException, IOException,
			ServiceException {
		String idOfEvent = request
				.getParameter(SessionConstants.EVENT_ID_ATTRIBUTE
						.getName());
		String locale = (String) session
				.getAttribute(SessionConstants.LOCALE_ATTRIBUTE.getName());
		int langId = (Integer) request.getSession().getAttribute(
				SessionConstants.LOCALE_ID_ATTRIBUTE.getName());
		String message = null;
		SiteLogic siteLogic = null;
		if (idOfEvent == null) {
			message = MessagesProperties.createPathProperties().getProperties(
					MessagesProperties.EVENT_DEL_ERROR, locale);
		} else {
			try {
				Integer eventId = Integer.parseInt(idOfEvent);
				AdminLogic adminLogic = new AdminLogic();
				siteLogic = new SiteLogic();
				if (adminLogic.deleteEvent(eventId)) {
					message = MessagesProperties.createPathProperties()
							.getProperties(MessagesProperties.EVENT_DEL_SUCCESS,
									locale);
					log.info(message);
				}
			} catch (ServiceException e) {
				log.error("Can't create logic class instance", e);
				throw new ServiceException("Can't create logic class instance",
						e);
			}
		}
		request.setAttribute(
				RequestConstants.EVENTS_LIST_ATTRIBUTE.getName(),
				siteLogic.getAllEvents(langId));
		request.setAttribute(SessionConstants.MENU_ITEM_ATTRIBUTE.getName(),
				SessionConstants.EVENTS_ATTRIBUTE.getName());
		request.setAttribute(SessionConstants.MESSAGE_ATTRIBUTE.getName(),
				message);
		request.setAttribute(SessionConstants.ANSWER_ATTRIBUTE.getName(),
				SessionConstants.EVENT_ANSWER_ATTRIBUTE.getName());
		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}

}
