package etool.cdimc.parser;

import etool.cdimc.stream.DataColumnStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class OracleParser implements Parser {
    @Override
    public OracleParser setConnection(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public DataColumnStream getOutput() throws SQLException {
        return null;
    }

    @Override
    public Set<String> getSchemas() {
        return null;
    }

    @Override
    public Set<String> getTables(String schema) {
        return null;
    }

    @Override
    public Set<String> getColumns(String table) {
        return null;
    }

    @Override
    public void getColumnValues(Set<String> columns) {
    }
}
