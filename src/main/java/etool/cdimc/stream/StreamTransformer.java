package etool.cdimc.stream;

import etool.cdimc.Constants;

import java.util.HashMap;
import java.util.LinkedList;

public class StreamTransformer {

    public static DataColumnStream transformJsonToDataStream(String json) {

        DataColumnStream dataColumnStream = new DataColumnStream("");
        String jsonArray = json.substring(json.indexOf('[')+1, json.indexOf(']'));
        String[] rows = jsonArray.split("},\\{");
        for(String row: rows) {
            String[] columns = row.split(",");
            for(String col: columns) {
                col = col.replaceFirst("\"", "'");
                col = col.replaceFirst("\"", "'");
                String colName = col.substring(col.indexOf("'")+1, col.lastIndexOf("'"));
                col = col.replace("}", "");
                col = col.replace("{", "");
                String colValue;
                if(col.contains("\"")){
                    colValue = col.substring(col.indexOf("\"")+1, col.lastIndexOf("\""));
                    colValue = colValue.replace("\n", "");
                } else {
                    colValue = col.substring(col.indexOf(":")+1, col.length()-1);
                    colValue = colValue.replace("}", "");
                    colValue = colValue.replace("{", "");
                    colValue = colValue.replace(" ", "");
                    colValue = colValue.replace(System.lineSeparator(), "");
                }
                dataColumnStream.addColumn(colName, colValue);
            }
        }

        Constants.logger().info("Transformed json to data stream: " + dataColumnStream.toString());
        return dataColumnStream;
    }

}
