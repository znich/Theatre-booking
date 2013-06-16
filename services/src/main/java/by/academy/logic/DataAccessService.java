package by.academy.logic;

import by.academy.dao.DaoFactory;
import by.academy.exception.ServiceException;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/12/13
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataAccessService {
    protected DaoFactory daoFactory;

    public DataAccessService()  {
        ResourceBundle res = ResourceBundle.getBundle(
                "properties/daoFactory");
        String daoFactoryName = res.getString("daoFactory");

        try {
            if (daoFactoryName != null) {
                Class daoFactoryClass = Class.forName(daoFactoryName);
                daoFactory = DaoFactory.instance(daoFactoryClass);
            } else {
                throw new ServiceException("Configure daoFactory.properties with a daoFactoryClass parameter!");
            }
        } catch (Exception ex) {
            try {
				throw new ServiceException("Can't find DAOFactory: " + daoFactoryName);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
