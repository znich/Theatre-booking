package by.academy.web.exception;

/**
 */
public class UIException extends Exception {
    public UIException()
    {
        super();
    }

    public UIException(String s)
    {
        super(s);
    }

    public UIException(String s, Throwable t)
    {
        super(s, t);
    }

    public UIException(Throwable t)
    {
        super(t);
    }
}
