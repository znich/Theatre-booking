package by.academy.dao.util;


import by.academy.dao.exception.HibernateUtilException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public enum HibernateUtil
{
    ;
    private static Log log = LogFactory.getLog(HibernateUtil.class);
    private static SessionFactory sessionFactory;
    private static final Lock singletoneLock = new ReentrantLock();
    /**
     * Method to get Session from SessionFactory.
     * @return currentSession() or openSession() from factory.
     * @throws HibernateUtilException if any Exception cached from Hibernate
     */
    public static Session getSession() throws HibernateUtilException
    {
        Session session;
        singletoneLock.lock();
        if (null == HibernateUtil.sessionFactory)
        {
            HibernateUtil.init();
        }
        singletoneLock.unlock();
        try
        {
            session = HibernateUtil.sessionFactory.getCurrentSession();
        }
        catch (Exception e)
        {
            session = HibernateUtil.sessionFactory.openSession();
        }
        return session;
    }

    private static void init() throws HibernateUtilException
    {
        try
        {
            Locale.setDefault(Locale.ENGLISH);
            Configuration conf = new Configuration();
            conf.setNamingStrategy(new CustomNamingStrategy());
            HibernateUtil.sessionFactory = conf.configure().buildSessionFactory();
        }
        catch (HibernateException ex)
        {
            log.error("Initial SessionFactory creation failed." + ex);
            throw new HibernateUtilException("Initial SessionFactory creation failed.", ex);
        }
    }
}

