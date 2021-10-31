package etool.cdimc.connectors;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLConnector {
    Connection connect(String url, String user, String password) throws ClassNotFoundException, SQLException;
    boolean testConnection(String url, String user, String password);
}
