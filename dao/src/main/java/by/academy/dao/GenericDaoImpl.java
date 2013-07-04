package by.academy.dao;

import by.academy.dao.exception.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 */
public class GenericDaoImpl<T, ID extends Serializable> implements IGenericDao<T, ID> {
    protected static Log log = LogFactory.getLog(GenericDaoImpl.class);
    private Class<T> persistentClass;
    private SessionFactory sessionFactory;

    public GenericDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void delEntity(ID id) throws DaoException {
        try {
            if (id != null) {
                T t = (T) getSession().get(persistentClass, id);
                getSession().delete(t);
            }
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }
    }

    @Override
    public void delEntity(T entity) throws DaoException {
        try {
            if (entity != null) {
                getSession().delete(entity);
            }
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }
    }

    @Override
    public T save(T entity) throws DaoException {
        try {
            ID id = (ID) getSession().save(entity);
            return getEntityById(id);
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }

    }

    @Override
    public T getEntityById(ID id) throws DaoException {
        try {
            T entity = (T) getSession().get(persistentClass, id);
            return entity;
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }
    }

    @Override
    public T getEntityById(ID id, int langId) throws DaoException {

        try {
            Criteria crit = getSession().createCriteria(persistentClass);
            crit.add(Restrictions.idEq(id));
            crit.add(Restrictions.eq("langId", langId));
            T t = (T) crit.uniqueResult();
            return t;
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> findAll() throws DaoException {
        return findByCriteria();
    }

    @Override
    public List<T> getParentEntities() throws DaoException {
        return findByCriteria(Restrictions.isNull("parent"));

    }

    @Override
    public List<T> findByCriteria(Criterion... criterion) throws DaoException {
        try {
            Criteria crit = getSession().createCriteria(persistentClass);
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return crit.list();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }
    }

    @Override
    public T findOneByCriteria(Criterion... criterion) throws DaoException {
        try {
            Criteria crit = getSession().createCriteria(persistentClass);
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return (T)crit.uniqueResult();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            throw new DaoException(e);
        }
    }
}
