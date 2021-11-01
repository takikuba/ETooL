package etool.cdimc.connectors;

import etool.cdimc.parser.PsqlParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnector implements SQLConnector {

    public Connection connect(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public boolean testConnection(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.warning("ERROR: Can't connect!");
            throwables.printStackTrace();
        }
        return false;
    }

}
