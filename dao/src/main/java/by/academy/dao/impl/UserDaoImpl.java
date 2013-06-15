package by.academy.dao.impl;

import by.academy.dao.IUserDao;
import by.academy.domain.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements IUserDao {
    @Override
    public User getUserByEmail(String email) {
        return findByCriteria( Restrictions.eq("email", email) ).get(0);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return findByCriteria( Restrictions.eq("email", email), Restrictions.eq("password", password) ).get(0);
    }
}
