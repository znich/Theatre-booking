package by.academy.dao.impl;

import by.academy.dao.IGenericDao;
import by.academy.dao.exception.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 */
public abstract class GenericDaoImpl<T, ID extends Serializable> implements IGenericDao<T, ID> {
    private static Log log = LogFactory.getLog(GenericDaoImpl.class);
    private Class<T> persistentClass;
    private Session session;

    public GenericDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        if (session == null){
            log.error("Session has not been set on DAO before usage");
            throw new IllegalStateException("Session has not been set on DAO before usage");
        }
        return session;
    }

    public void setSession(Session s) {
        this.session = s;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public void delEntity(ID id) throws IllegalArgumentException, DaoException {
        if (id == null) {
            throw new IllegalArgumentException("GenericDaoImpl.ErrorIdNull");
        }

        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();
            T t = (T) session.get(getPersistentClass(), id);

            session.delete(t);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void delEntity(T entity) throws IllegalArgumentException, DaoException {
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity for saving cannot be null!");
        }
        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();
            session.delete(entity);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public T save(T entity) throws IllegalArgumentException, DaoException {
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity for saving cannot be null!");
        }
        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();
            session.saveOrUpdate(entity);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
         }
        return entity;
    }

    @Override
    public T getEntityById(ID id) throws IllegalArgumentException, DaoException {
        if (id == null) {
            throw new IllegalArgumentException("GenericDaoImpl.ErrorIdNull");
        }

        T t = null;
        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();
            t = (T) session.get(getPersistentClass(), id);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public T getEntityById(ID id, int langId) throws IllegalArgumentException, DaoException {
        if (id == null && langId < 1) {
            throw new IllegalArgumentException("GenericDaoImpl.ErrorIdNull");
        }
        T t = null;

        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();

            Criteria crit = session.createCriteria(getPersistentClass());
            crit.add(Restrictions.idEq(id));
            crit.add(Restrictions.eq("langId", langId));
            t = (T) crit.uniqueResult();
            tr.commit();
        } catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public List<T> findAll() throws DaoException {
        return findByCriteria();
    }

    @Override
    public List<T> findAll(int langId) throws DaoException {
        return findByCriteria(Restrictions.eq("langId", langId));

    }

    protected List<T> findByCriteria(Criterion... criterion) throws DaoException {
        Transaction tr = null;
        try {
            Session session = getSession();
            tr = session.beginTransaction();

            Criteria crit = session.createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();

        }catch (HibernateException e) {
            log.error("Error was thrown in DAO", e);
            if (tr != null) {
                tr.rollback();
            }
            throw new DaoException(e);
        }
    }
}
