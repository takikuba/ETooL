package etool.cdimc.parser;

import etool.cdimc.stream.DataColumnStream;

import java.sql.Connection;
import java.sql.SQLException;

public class OracleParser implements Parser {
    @Override
    public OracleParser setConnection(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public DataColumnStream getOutput() throws SQLException {
        return null;
    }
}
