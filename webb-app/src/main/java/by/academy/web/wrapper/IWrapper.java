package by.academy.web.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/22/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IWrapper {
    HttpServletRequest getRequest();
    HttpServletResponse getResponse();
    HttpSession getSession();
}
