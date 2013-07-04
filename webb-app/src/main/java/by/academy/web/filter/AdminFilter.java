package by.academy.web.filter;

import by.academy.domain.User;
import by.academy.domain.UserRole;
import by.academy.web.util.PathProperties;
import by.academy.web.util.SessionConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 */
public class AdminFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if ((request != null)
				&& (request instanceof HttpServletRequest)
				&& (((HttpServletRequest) request).getSession().getAttribute(
						SessionConstants.USER_ATTRIBUTE.getName()) instanceof User)) {
			User user = (User) ((HttpServletRequest) request).getSession()
					.getAttribute(SessionConstants.USER_ATTRIBUTE.getName());
			if (user.getRole().equals(UserRole.ADMIN)) {
				chain.doFilter(request, response);
			}
		} else {
			RequestDispatcher dispatcher;
			String url = PathProperties.createPathProperties().getProperty(
					PathProperties.LOGIN_PAGE);
			dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
	}

	@Override
	public void destroy() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}
}
