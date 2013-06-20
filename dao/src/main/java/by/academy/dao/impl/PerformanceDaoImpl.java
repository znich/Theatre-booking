package by.academy.dao.impl;

import by.academy.dao.IPerformanceDao;
import by.academy.dao.exception.DaoException;
import by.academy.domain.Performance;
import by.academy.domain.Property;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class PerformanceDaoImpl extends GenericDaoImpl<Performance, Integer> implements IPerformanceDao {


    @Override
    public List<Performance> getPerformancesByCategory(Integer catId) throws DaoException {
        return findByCriteria( Restrictions.eq("category", catId) );
    }
}
