package by.academy.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 02.06.13
 * Time: 2:19
 * To change this template use File | Settings | File Templates.
 */
public class ServiceException extends Exception {
    public ServiceException()
    {
        super();
    }

    public ServiceException(String s)
    {
        super(s);
    }

    public ServiceException(String s, Throwable t)
    {
        super(s, t);
    }

    public ServiceException(Throwable t)
    {
        super(t);
    }

}
