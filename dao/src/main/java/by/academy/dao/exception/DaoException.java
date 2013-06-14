package by.academy.dao.exception;
/**
 * Исключительная ситуация или исключение, которое может быть выброшено в ходе выполнения
 * методов dao.
 *
 * @author Siarhei Poludvaranin
 * @see     java.io.InputStream
 * @see     java.io.OutputStream
 */
public
class DaoException extends Exception {


    /**
     * Constructs an {@code DaoException} with {@code null}
     * as its error detail message.
     */
    public DaoException() {
        super();
    }

    /**
     * Создает новый объект <b>DAOException</b> с текстовым сообщением об ошибке.
     *
     * @param message - <b>String</b> строка с текстом сообщения об ошибке.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code DaoException} with the specified detail message
     * and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code DaoException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for IO exceptions that are little more
     * than wrappers for other throwables.
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
