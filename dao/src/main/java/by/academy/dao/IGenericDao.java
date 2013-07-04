package by.academy.dao;

import by.academy.dao.exception.DaoException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 */
public interface IGenericDao<T, ID extends Serializable> {

    boolean delEntity(ID id) throws DaoException;

    boolean delEntity(T entity) throws DaoException;

    T save(T entity) throws DaoException;

    T getEntityById(ID id) throws DaoException;

    T getEntityById(ID id, int langId) throws DaoException;

    List<T> findAll() throws DaoException;

    List<T> getParentEntities() throws DaoException;

    List<T> findByCriteria(Criterion... criterion) throws DaoException;

    T findOneByCriteria(Criterion... criterion) throws DaoException;

}
