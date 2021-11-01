package etool.cdimc.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleSQLConnector implements SQLConnector {
    @Override
    public Connection connect(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean testConnection(String url, String user, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.warning("ERROR: Can't connect!");
            throwables.printStackTrace();
        }
        return false;
    }
}
