package by.academy.dao;

import by.academy.domain.Performance;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/6/13
 * Time: 7:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPerformanceDao extends IGenericDao<Performance, Integer> {
    List<Performance> getPerformancesByCategory(Integer catId);
}
