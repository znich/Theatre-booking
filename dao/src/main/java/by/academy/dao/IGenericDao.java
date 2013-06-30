package by.academy.dao;

import by.academy.dao.exception.DaoException;
import org.hibernate.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This is a generic interface that exposes generic methods  for common CRUD functionality.
 * This methods include CRUD operations with ability to get all entity.
 *
 * @param <T> Domain class to work with
 * @author Siarhei Poludvaranin
 */
public interface IGenericDao<T, ID extends Serializable> {

    boolean delEntity(ID id) throws IllegalArgumentException, DaoException;

    boolean delEntity(T entity) throws IllegalArgumentException, DaoException;

    T save(T entity) throws IllegalArgumentException, DaoException;

    T getEntityById(ID id) throws IllegalArgumentException, DaoException;

    T getEntityById(ID id, int langId) throws IllegalArgumentException, DaoException;

    List<T> findAll() throws DaoException;

    List<T> getParentEntities() throws DaoException;

}
