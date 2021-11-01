package etool.cdimc.connectors;

import etool.cdimc.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public interface SQLConnector {
    Logger logger = Constants.logger();
    Connection connect(String url, String user, String password) throws ClassNotFoundException, SQLException;
    boolean testConnection(String url, String user, String password);
}
