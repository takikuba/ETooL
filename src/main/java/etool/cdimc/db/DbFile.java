package etool.cdimc.db;

import etool.cdimc.Constants;
import etool.cdimc.models.Table;
import etool.cdimc.repository.Repository;
import etool.cdimc.stream.DataColumnStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class DbFile {

    public static DbTableFile getDbFile(DataColumnStream data, Repository repo) throws IOException {
        String out = "";
        for(String col: data.getColumns()) {
            out += "column." + col + "$";
        }
        out += "\n";

        for(int i=0; i < data.getColumnValuesSize(); i++) {
            for(String col: data.getColumns()) {
                out += data.getIndexColumnValue(col, i) + "$";
            }
            out +=  "\n";
        }
        Table table = new Table(data.getName(), data.getColumns());
        File repoDir = new File(Constants.REPOSITORIES_PATH + repo.getLocation());
        for(File f: Objects.requireNonNull(repoDir.listFiles())){
            if(f.getName().equals(table.getLocation())){
                Constants.logger().warning("Table with this name already exist!");
                table = table.changeName();
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.REPOSITORIES_PATH + repo.getLocation() + "/" + table.getName() + ".cef"));
        writer.write(out);

        writer.close();

        return new DbTableFile(table, new File(Constants.REPOSITORIES_PATH + repo.getLocation() + "/" + table.getName() + ".cef"));
    }

    public static void cleanRepository(Repository repo) {
        File file = new File(Constants.REPOSITORIES_PATH + repo.getLocation());
        File[] files = file.listFiles();
        for(File f: files) {
            if(f.getName().endsWith("mysql")) {
                if(f.delete()){
                    Constants.logger().info(f.getName() + " file deleted successfully!");
                } else {
                    Constants.logger().warning("Failed to delete " + f.getName() + " file!");
                }
            }
        }
    }

    public static String getOutputFormat(DataColumnStream data) {
        String out = "";
        for(String col: data.getColumns()) {
            out += "column." + col + "$";
        }
        out += "\n";

        for(int i=0; i < data.getColumnValuesSize(); i++) {
            for(String col: data.getColumns()) {
                out += data.getIndexColumnValue(col, i) + "$";
            }
            out +=  "\n";
        }

        return out;
    }

    public static class DbTableFile {

        private final Table table;
        private final File file;

        public DbTableFile(Table table, File file) {
            this.table = table;
            this.file = file;
        }

        public Table getTable() {
            return table;
        }

        public File getFile() {
            return file;
        }
    }

}
