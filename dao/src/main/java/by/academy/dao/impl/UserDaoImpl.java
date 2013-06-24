package by.academy.dao.impl;

import by.academy.dao.IUserDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    public User getUserByEmail(String email) throws DaoException {
        return findByCriteria( Restrictions.eq("email", email) ).get(0);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws DaoException {
        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();

            Criteria crit = session.createCriteria(getPersistentClass());
            crit.add(Restrictions.eq("email", email));
            crit.add(Restrictions.eq("password", password));

            return (User)crit.uniqueResult();

        }catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
            throw new DaoException(e);
        }
    }
}
