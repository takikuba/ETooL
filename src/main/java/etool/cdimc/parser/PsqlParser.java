package etool.cdimc.parser;

import etool.cdimc.Constants;
import etool.cdimc.models.BaseItem;
import etool.cdimc.models.RelationalModel;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class PsqlParser {

    private static Logger logger = Constants.logger();

    public static Set<String> SCHEMAS_BLACKLIST = Set.of("pg_toast", "pg_catalog", "information_schema");

    private Connection connection;
    private RelationalModel.DataBase host;
    private RelationalModel.Schema schema;
    private RelationalModel.Table table;
    private Set<RelationalModel.Column> columns;

    public PsqlParser(Connection connection) throws SQLException {
        this.connection = connection;
        this.host = new RelationalModel.DataBase(connection.getCatalog());
        logger.info("Get following database: " + connection.getCatalog());
        getSchemas();
        schema = new RelationalModel.Schema(host,"ETL");
        table = new RelationalModel.Table(schema,"students");
        columns = Set.of(new RelationalModel.Column(table,"name"), new RelationalModel.Column(table, "album"));
        getTables();
        getColumns();
        getColumnValues();
    }

    private ArrayList<BaseItem> getSchemas() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select nspname from pg_catalog.pg_namespace;");

        String schemaName = "";
        while(result.next()){
            schemaName = result.getString("nspname");
            if(!SCHEMAS_BLACKLIST.contains(schemaName)){
                host.getOrCreateSchema(result.getString("nspname"));
            }
        }
        logger.info("Extract following schemas: " + host.getChildrenNames());
        return host.getChildren();
    }

    private ArrayList<BaseItem> getTables() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from information_schema.tables where table_schema = '" + schema.getName() +"';");
        String table = "";
        while(result.next()) {
            table = result.getString("table_name");
            schema.getOrCreateChild(table);
        }
        logger.info("Extract following tables: " + schema.getChildrenNames());

        return schema.getChildren();
    }

    private ArrayList<BaseItem> getColumns() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from information_schema.columns where table_name = '" + table.getName() + "';");
        String column = "";
        while(result.next()) {
            column = result.getString("column_name");
            table.getOrCreateChild(column);
        }
        logger.info("Extract following columns: " + table.getChildrenNames());
        return table.getChildren();
    }

    private HashMap<RelationalModel.Column, ArrayList<String>> getColumnValues() throws SQLException {
        Statement statement = connection.createStatement();
        HashMap<RelationalModel.Column, ArrayList<String>> columnValues = new HashMap<>();
        for(RelationalModel.Column col: columns){
            String query = "select " + col.getName() + " from \"" + schema.getName() + "\"." + table.getName() + ";";
            ResultSet result = statement.executeQuery(query);
            String column = "";
            ArrayList<String> values = new ArrayList<>();
            while(result.next()) {
                column = result.getString(col.getName());
                values.add(column);
            }
            columnValues.put(col, values);
        }
        logger.info("Extract following columns: " + columnValues.toString());
        return columnValues;
    }

}
