package etool.cdimc.stream;

import etool.cdimc.Constants;

import java.util.HashMap;
import java.util.LinkedList;

public class StreamTransformer {

    public static DataColumnStream transformJsonToDataStream(String json) {
        System.out.println(json);

        DataColumnStream dataColumnStream = new DataColumnStream("");

        String jsonArray = json.substring(json.indexOf('[')+1, json.indexOf(']'));
        String[] rows = jsonArray.split("},\\{");
//        String[] columns = rows[0].split(",");
        LinkedList<String[]> columnsList = new LinkedList<>();
        for(String row: rows) {
            columnsList.add(row.split(","));
        }

        for(String[] columns: columnsList) {
            for(String col: columns) {
                col = col.replaceFirst("\"", "'");
                col = col.replaceFirst("\"", "'");
                String colName = col.substring(col.indexOf("'")+1, col.lastIndexOf("'"));
                col = col.replace("}", "");
                col = col.replace("{", "");
                System.out.println("col" + col);
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
        System.out.println(dataColumnStream.getWriter());
        return dataColumnStream;
    }

}
