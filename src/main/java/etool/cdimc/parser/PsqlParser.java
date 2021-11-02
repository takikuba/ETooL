package etool.cdimc.parser;

import etool.cdimc.Constants;
import etool.cdimc.models.RelationalModel;
import etool.cdimc.stream.DataColumnStream;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class PsqlParser implements Parser {

    private final Logger logger = Constants.logger();

    public static Set<String> SCHEMAS_BLACKLIST = Set.of("pg_toast", "pg_catalog", "information_schema");

    private Connection connection;
    private RelationalModel.DataBase host;
    private RelationalModel.Schema schema;
    private RelationalModel.Table table;
    private Set<RelationalModel.Column> columns;
    private Set<String> columnsNames;

    public PsqlParser() {}

    public PsqlParser(Connection connection) throws SQLException {
        this.connection = connection;
        this.host = new RelationalModel.DataBase(connection.getCatalog(), null);
        logger.info("Get following database: " + connection.getCatalog());
        getSchemas();
        schema = host.getOrCreateSchema("ETL");
        table = schema.getOrCreateTable("students");
        columns = Set.of(table.getOrCreateColumn("name"), table.getOrCreateColumn("album"));
        getTables();
        getColumns();
    }

    public PsqlParser setConnection(Connection connection) throws SQLException {
        return new PsqlParser(connection);
    }

    public DataColumnStream getOutput() throws SQLException {
        return getColumnValues();
    }

    private Collection<RelationalModel.Schema> getSchemas() throws SQLException {
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

    public Collection <RelationalModel.Table> getTables(String schema) throws SQLException {
        if(host.getChildrenNames().contains(schema)){
            this.schema = host.getOrCreateSchema(schema);
            return getTables();
        } else {
            logger.warning("ERROR: schema not found!");
        }
        return null;
    }

    private Collection<RelationalModel.Table> getTables() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from information_schema.tables where table_schema = '" + schema.getName() +"';");
        String table = "";
        while(result.next()) {
            table = result.getString("table_name");
            schema.getOrCreateTable(table);
        }
        logger.info("Extract following tables: " + schema.getChildrenNames());

        return schema.getChildren();
    }

    public Collection<RelationalModel.Column> getColumns(String table) throws SQLException {
        if(schema.getChildrenNames().contains(table)) {
            this.table = schema.getOrCreateTable(table);
            getColumns();
        } else {
            logger.warning("ERROR: schema not found!");
        }
        return null;
    }

    private Collection<RelationalModel.Column> getColumns() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from information_schema.columns where table_name = '" + table.getName() + "';");
        String column = "";
        while(result.next()) {
            column = result.getString("column_name");
            table.getOrCreateColumn(column);
        }
        columnsNames = table.getChildrenNames();
        logger.info("Extract following columns: " + table.getChildrenNames());
        return table.getChildren();
    }

    public DataColumnStream getColumnValues(Set<String> columns) throws SQLException {
        this.columns = new HashSet<RelationalModel.Column>();
        columns.forEach( col -> {
                    if (columnsNames.contains(col)) {
                        this.columns.add(table.getOrCreateColumn(col));
                    } else {
                        logger.warning(col + " can't be process!");
                    }
                });
        return getColumnValues();
    }

    private DataColumnStream getColumnValues() throws SQLException {
        Statement statement = connection.createStatement();
        DataColumnStream data = new DataColumnStream(table.getName());
        for(RelationalModel.Column col: columns){
            String query = "select " + col.getName() + " from \"" + schema.getName() + "\"." + table.getName() + ";";
            ResultSet result = statement.executeQuery(query);
            data.addColumn(col.getName());
            while(result.next()) {
                data.addColumnValue(col.getName(), result.getString(col.getName()));
            }
        }

        logger.info("Extract values for following columns: " + data.toString());
        return data;
    }

}
