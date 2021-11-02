package etool.cdimc.components;

import etool.cdimc.connectors.PostgreSQLConnector;
import etool.cdimc.db.DbFile;
import etool.cdimc.etl.EtlActions;
import etool.cdimc.parser.PsqlParser;
import etool.cdimc.repository.Repository;
import etool.cdimc.repository.Vendor;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;

public class DbTransformFrame extends JFrame {

    public DbTransformFrame(EtlActions etlActions){
        DbConnectionFrame connectionFrame = new DbConnectionFrame();
//        Connection psql = new PostgreSQLConnector().connect("jdbc:postgresql://localhost/etlTEST", "admin2", "admin");
//        PsqlParser parser = new PsqlParser(psql);
//
//        Repository repository = new Repository("Repo4", Vendor.JSON, "Repo4_JSON");
//        File file = DbFile.getDbFile(parser.getOutput(), repository);
//        Vendor inputVendor = Vendor.MYSQL;
//        etlActions.extract(inputVendor, file, etlActions.repository);
    }

}
