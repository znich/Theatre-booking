package by.academy.DAO.admin;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEAdminDAO implements AdminDAO {

    private Connection connection = null;

    public ORACLEAdminDAO(Connection connection) {
        this.connection = connection;
    }
}
