package by.academy.DAO.util;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 30.05.13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {
    private SessionFactory sessionFactory;
    private static Logger log = Logger.getLogger(HibernateUtil.class);
    private final ThreadLocal sessions = new ThreadLocal();

    private HibernateUtil(){
    	Locale.setDefault(Locale.ENGLISH);
    	try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession(){
      Session session = (Session)sessions.get();
      if(session == null){
          session = sessionFactory.openSession();
      }
      return session;
    }
}
