package by.academy.DAO.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 25.05.13
 * Time: 22:13
 * Пул соединений.
 */
public class ConnectionPool {

    private static DataSource ds;

    private ConnectionPool() {
    }

    static public DataSource getConnectionPool() {
        if (ds == null) {
            Locale.setDefault(Locale.ENGLISH);
            try {
                Context initContext = new InitialContext();
                Context envContext  = (Context)initContext.lookup("java:/comp/env");
                ds = (DataSource)envContext.lookup("jdbc/db");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return ds;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
