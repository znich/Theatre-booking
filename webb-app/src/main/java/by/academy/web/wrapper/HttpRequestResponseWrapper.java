package by.academy.web.wrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/22/13
 * Time: 10:05 AM
 */
public class HttpRequestResponseWrapper implements IWrapper {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private static Log log = LogFactory.getLog(HttpRequestResponseWrapper.class);

    public HttpRequestResponseWrapper(HttpServletRequest request, HttpServletResponse response) throws WrapperException
    {
        if (request != null)
        {
            this.request = request;
        }
        else
        {
            log.error("Attempt to create empty RequestResponseWrapper (with null in HttpServletRequest request parameter)");
            throw new WrapperException("Attempt to create empty RequestResponseWrapper (with null in HttpServletRequest request parameter)");
        }
        if (response!= null)
        {
            this.response = response;
        }
        else
        {
            log.error("Attempt to create empty RequestResponseWrapper (with null in HttpServletResponse response parameter)");
            throw new WrapperException("Attempt to create empty RequestResponseWrapper (with null in HttpServletResponse response parameter)");
        }
    }

    public void setRequest(HttpServletRequest request)
    {
        if (null != request)
        {
            this.request = request;
        }
    }

    public void setResponse(HttpServletResponse response)
    {
        if (null != response)
        {
            this.response = response;
        }
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }

    @Override
    public HttpSession getSession() {
        return request.getSession();
    }
}
