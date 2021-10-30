package etool.cdimc.db;

import etool.cdimc.Constants;
import etool.cdimc.repository.Repository;
import etool.cdimc.stream.DataColumnStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DbFile {

    public static File getDbFile(DataColumnStream data, Repository repo) throws IOException {
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
        BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.REPOSITORIES_PATH + repo.getLocation() + "/" + data.getName() + ".tmp"));
        writer.write(out);

        writer.close();
        return new File(Constants.REPOSITORIES_PATH + repo.getLocation() + "/" + data.getName() + ".tmp");
    }

    public static void cleanRepository(Repository repo) {
        File file = new File(Constants.REPOSITORIES_PATH + repo.getLocation());
        File[] files = file.listFiles();
        for(File f: files) {
            if(f.getName().endsWith("tmp")) {
                f.delete();
            }
        }
    }

}
