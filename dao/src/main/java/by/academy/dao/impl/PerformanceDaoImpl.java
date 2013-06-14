package by.academy.dao.impl;

import by.academy.dao.IPerformanceDao;
import by.academy.domain.Performance;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/7/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class PerformanceDaoImpl extends GenericDaoImpl<Performance, Integer> implements IPerformanceDao {
    @Override
    public List<Performance> getPerformancesByCategory(Integer catId) {
        return findByCriteria( Restrictions.eq("category", catId) );
    }
}
