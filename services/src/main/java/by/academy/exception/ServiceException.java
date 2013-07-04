package by.academy.exception;

/**
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
