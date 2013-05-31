package by.academy.DAO.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionPool {
    public static final String PROPERTIES_FILE =
            "properties/database";
    public static final int DEFAULT_POOL_SIZE = 10;
    /** single instance */
    private static ConnectionPool instance;
    /** free connections queue */
    private BlockingQueue<Connection> connectionQueue;

    private ConnectionPool (String driver, String url,
                            String user, String password, int poolSize)
            throws ClassNotFoundException, SQLException {
        Class.forName (driver);
        Locale.setDefault(Locale.ENGLISH);
        connectionQueue
                = new ArrayBlockingQueue<Connection>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection
                    = DriverManager.getConnection(url, user, password);
            connectionQueue.offer (connection);
        }
    }

    public static void init () throws SQLException {
        if (instance == null) {
            ResourceBundle rb = ResourceBundle.getBundle(
                    PROPERTIES_FILE);
            String driver = rb.getString ("db.driver");
            String url = rb.getString ("db.url");
            String user = rb.getString ("db.user");
            String password = rb.getString ("db.password");
            String poolSizeStr = rb.getString ("db.poolsize");
            int poolSize = (poolSizeStr != null) ?
                    Integer.parseInt(poolSizeStr) : DEFAULT_POOL_SIZE;
            try {
                //"Trying to create pool of connections..."/
                instance = new ConnectionPool (driver, url,
                        user, password, poolSize);
                //"Connection pool succesfully initialized"
            } catch (ClassNotFoundException e) {
                //"Driver " + driver + " not found"
                throw new RuntimeException (e);
            }
        }
    }

    public static void dispose () throws SQLException {
        if (instance != null) {
            instance.clearConnectionQueue ();
            instance = null;
            //"Connection pool succesfully disposed"
        }
    }

    public static ConnectionPool getInstance () {
        return instance;
    }

    public Connection takeConnection () {
        Connection connection = null;
        System.out.println("takeConnection");
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            //"Free connection waiting interrupted.
            // Returned `null` connection", e
        }
        return connection;
    }

    public void releaseConnection (Connection connection) {
        try {
            if (!connection.isClosed ()) {
                if (!connectionQueue.offer (connection)) {
                    //"Connection not added. Possible `leakage` of
                    // connections"
                	
                } else {System.out.println("pool size-"+connectionQueue.size());}
            } else {
                //"Trying to release closed connection. Possible
                // `leakage` of connections"
            }
        } catch (SQLException e) {
            //"SQLException at conection isClosed () checking.
            // Connection not added", e
        }
    }

    private void clearConnectionQueue () throws SQLException {
        Connection connection;
        while ((connection = connectionQueue.poll ()) != null) {
            /* see java.sql.Connection#close () javadoc */
            if (!connection.getAutoCommit ()) {
                connection.commit ();
            }
            connection.close ();
        }
    }
}
