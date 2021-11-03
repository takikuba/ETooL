package etool.cdimc.parser;

import etool.cdimc.stream.DataColumnStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface Parser {
    Parser setConnection(Connection connection) throws SQLException;
    DataColumnStream getOutput() throws SQLException;
    Set<String> getSchemas();
    Set<String> getTables(String schema);
    Set<String> getColumns(String table);
    DataColumnStream getColumnValues(Set<String> columns);
}
