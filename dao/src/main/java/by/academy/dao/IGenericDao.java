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

    void delEntity(ID id) throws IllegalArgumentException;

    void delEntity(T entity) throws IllegalArgumentException;

    T save(T entity) throws IllegalArgumentException;

    T getEntityById(ID id) throws IllegalArgumentException;

    T getEntityById(ID id, int langId) throws IllegalArgumentException;

    List<T> findAll();

    List<T> findAll(int langId);

}
