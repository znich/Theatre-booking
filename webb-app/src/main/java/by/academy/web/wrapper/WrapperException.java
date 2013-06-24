package by.academy.web.wrapper;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/22/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class WrapperException extends Exception {
    private static final long serialVersionUID = 1L;

    public WrapperException()
    {
        super();
    }

    public WrapperException(String s)
    {
        super(s);
    }

    public WrapperException(Throwable e)
    {
        super(e);
    }

    public WrapperException(String s, Throwable e)
    {
        super(s, e);
    }
}
