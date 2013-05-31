package by.academy.DAO.exception;

/**
 * Исключительная ситуация или исключение, которое может быть выброшено в ходе выполнения
 * методов DAO.
 *
 * @author Siarhei Poludvaranin
 */
public class DaoException extends Exception {

    /**
     * Создает новый объект <b>DaoException</b> с текстовым сообщением об ошибке.
     *
     * @param message - <b>String</b> строка с текстом сообщения об ошибке.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Создает новый объект <b>DAOException</b> на основе другой исключительной
     * ситуации.
     *
     * @param e -
     *            <b>Exception</b>, на основе которой создается новый объект
     *            <b>DaoException</b>.
     */
    public DaoException(Exception e) {
        super(e);
    }
}
