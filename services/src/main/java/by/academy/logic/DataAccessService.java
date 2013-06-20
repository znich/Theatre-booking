package by.academy.logic;

import by.academy.dao.DaoFactory;
import by.academy.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/12/13
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataAccessService {
    protected DaoFactory daoFactory;
    private static Log log = LogFactory.getLog(DataAccessService.class);

    public DataAccessService() throws ServiceException {
        String daoFactoryName = null;

        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("daoFactory.properties"));
            daoFactoryName = prop.getProperty("daoFactory");

            if (daoFactoryName != null) {
                Class daoFactoryClass = Class.forName(daoFactoryName);
                daoFactory = DaoFactory.instance(daoFactoryClass);
            } else {
                log.error("DataAccessService Error: Configure daoFactory.properties with a daoFactory parameter!");
                throw new ServiceException();
            }
        }catch (ClassNotFoundException ex){
            log.error("Can't find DAOFactory: " + daoFactoryName, ex);
        }
        catch (Exception ex) {
            throw new ServiceException();
        }
    }
}
