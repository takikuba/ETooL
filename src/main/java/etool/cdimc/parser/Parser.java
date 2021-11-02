package etool.cdimc.parser;

import etool.cdimc.stream.DataColumnStream;

import java.sql.Connection;
import java.sql.SQLException;

public interface Parser {
     Parser setConnection(Connection connection) throws SQLException;
    DataColumnStream getOutput() throws SQLException;
}
