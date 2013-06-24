package by.academy.dao.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 31.05.13
 * Time: 2:34
 * To change this template use File | Settings | File Templates.
 */
public enum HibernateUtil {
    ;
    private static final ThreadLocal<Session> sessions = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory;
    public static Log log = LogFactory.getLog(HibernateUtil.class);

    private static void getSessionFactory() {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Configuration conf = new Configuration();
            conf.setNamingStrategy(new CustomNamingStrategy());
            sessionFactory = conf.configure().buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        Session session = (Session) sessions.get();
        if (session == null) {
            HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            sessions.set(session);
        }
        return session;
    }
}
