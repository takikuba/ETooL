package etool.cdimc.parser;

import etool.cdimc.models.RelationalModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PsqlParser {

    private Connection connection;

    public PsqlParser(Connection connection) throws SQLException {
        this.connection = connection;
        RelationalModel.DataBase host = new RelationalModel.DataBase(connection.getCatalog());
        System.out.println(connection.getCatalog());
    }

    private ArrayList<String> getTables() throws SQLException {
        String schemaName = connection.nativeSQL("");
        return null;
    }

}
