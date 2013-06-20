package by.academy.dao;

import by.academy.dao.exception.DaoException;
import by.academy.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IUserDao extends IGenericDao<User, Integer> {
    User getUserByEmail(String email) throws DaoException;
    User getUserByEmailAndPassword(String email, String password) throws DaoException;
}
