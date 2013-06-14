package by.academy.dao.impl;

import by.academy.dao.IGenericDao;
import by.academy.dao.exception.DaoException;
import by.academy.dao.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/5/13
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericDaoImpl<T, ID extends Serializable> implements IGenericDao<T, ID> {
    private static Logger log = Logger.getLogger(HibernateUtil.class);
    private Class<T> persistentClass;
    private Session session;

    public GenericDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        if (session == null)
            throw new IllegalStateException("Session has not been set on DAO before usage");
        return session;
    }

    public void setSession(Session s) {
        this.session = s;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public void delEntity(ID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("GenericDaoImpl.ErrorIdNull");
        }

        Session session = getSession();
        T t = (T) session.get(getPersistentClass(), id);
        session.delete(t);
    }

    @Override
    public void delEntity(T entity) throws IllegalArgumentException {
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity for saving cannot be null!");
        }
        Session session = getSession();
        session.delete(entity);
    }

    @Override
    public T save(T entity) throws IllegalArgumentException {
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity for saving cannot be null!");
        }
        Session session = getSession();
        session.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public T getEntityById(ID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("GenericDaoImpl.ErrorIdNull");
        }

        T t = null;
        Session session = getSession();
        t = (T) session.get(getPersistentClass(), id);
        return t;
    }

    @Override
    public T getEntityById(ID id, int langId) throws IllegalArgumentException {
        if (id == null && langId < 1) {
            throw new IllegalArgumentException("GenericDaoImpl.ErrorIdNull");
        }
        T t = null;

        Session session = getSession();
        Criteria crit = session.createCriteria(getPersistentClass());
        crit.add(Restrictions.idEq(id));
        crit.add(Restrictions.eq("langId", langId));
        t = (T) crit.uniqueResult();
        return t;
    }

    @Override
    public List<T> findAll() {
        return findByCriteria();
    }

    @Override
    public List<T> findAll(int langId) {
        return findByCriteria(Restrictions.eq("langId", langId));
    }

    protected List<T> findByCriteria(Criterion... criterion) {
        Session session = getSession();
        Criteria crit = session.createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }
}
