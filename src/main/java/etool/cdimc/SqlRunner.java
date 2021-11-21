package etool.cdimc;

import etool.cdimc.connectors.DbConnectors;
import etool.cdimc.connectors.PostgreSQLConnector;
import etool.cdimc.connectors.SQLConnector;
import etool.cdimc.db.DbFile;
import etool.cdimc.etl.EtlActions;
import etool.cdimc.parser.Parser;
import etool.cdimc.parser.PsqlParser;
import etool.cdimc.repository.Repository;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class SqlRunner {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Repository repository = new Repository("Repo1", "Repo1_TXT");
        EtlActions etlActions = new EtlActions(repository);
        Connection connection = new PostgreSQLConnector().connect(DbConnectors.POSTGRESQL.getUrl(), DbConnectors.POSTGRESQL.getUser(), DbConnectors.POSTGRESQL.getPassword());
        Parser parser = new PsqlParser(connection);
        parser.getTables("ETL");
        parser.getColumns("cars");
        parser.getColumnValues(Set.of("model", "mark"));

        DbFile.DbTableFile dbTableFile = DbFile.getDbFile(parser.getOutput(), repository);
        etlActions.setFile(dbTableFile.getFile());
        etlActions.setTable(dbTableFile.getTable());
        etlActions.extract();
        etlActions.load();
    }

}
