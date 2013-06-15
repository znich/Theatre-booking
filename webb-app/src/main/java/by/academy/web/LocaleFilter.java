package by.academy.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 11:28
 *  Фильтр. �?зменяет кодировку запроса.
 */
public class LocaleFilter implements Filter {
    public enum Languages {
        RU,
        EN
    }
    public LocaleFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception java.io.IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF8");
        response.setCharacterEncoding("UTF8");
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String lang = "ru";
        if (request.getParameter("lang") != null) {
            lang = request.getParameter("lang");
            httpReq.getSession().setAttribute("lang", lang);

        }
        if (httpReq.getSession().getAttribute("lang") == null) {
            lang = httpReq.getLocale().getLanguage();
            httpReq.getSession().setAttribute("lang", lang);

        }

        switch (Languages.valueOf(lang.toUpperCase())) {
            case RU:
                httpReq.getSession().setAttribute("langId", 1);
                break;
            case EN:
                httpReq.getSession().setAttribute("langId", 2);
                break;
            default:
                httpReq.getSession().setAttribute("langId", 2);
                break;
        }
        doBeforeProcessing(request, response);
        chain.doFilter(request, response);
        doAfterProcessing(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
    }


}
