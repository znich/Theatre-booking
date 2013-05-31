package by.academy.DAO.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class CannotTakeConnectionException extends Exception {
    public CannotTakeConnectionException(){
        super();
    }
    public CannotTakeConnectionException(String msg){
        super(msg);
    }
}
