package etool.cdimc.connectors;

import etool.cdimc.Constants;

import javax.swing.*;

public enum DbConnectors {

    POSTGRESQL("PostgreSQL", "jdbc:postgresql://host:port/database", "user", "password", new ImageIcon(Constants.CONNECTORS_ICON + "postgresql.png"), new PostgreSQLConnector()),
    ORACLE("Oracle", "jdbc:oracle:thin:@host:port/service", "user", "password", new ImageIcon(Constants.CONNECTORS_ICON + "oracle.jpg"), new OracleSQLConnector());

    private final String name;
    private final String url;
    private final String user;
    private final String password;
    private final ImageIcon icon;
    private final SQLConnector connector;

    DbConnectors(String name, String url, String user, String password, ImageIcon icon, SQLConnector connector) {
        this.name = name;
        this.url = url;
        this.user = user;
        this.password = password;
        this.icon = icon;
        this.connector = connector;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public SQLConnector getConnector() {
        return connector;
    }

}
