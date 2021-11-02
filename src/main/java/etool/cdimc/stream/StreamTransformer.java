package etool.cdimc.stream;

import etool.cdimc.Constants;

public class StreamTransformer {

    public static DataColumnStream transformJsonToDataStream(String json) {

        DataColumnStream dataColumnStream = new DataColumnStream("");

        String jsonArray = json.substring(json.indexOf('[')+1, json.indexOf(']'));
        String[] rows = jsonArray.split("},\\{");
        String[] columns = rows[0].split(",");


        for(String col: columns) {
            col = col.replaceFirst("\"", "'");
            col = col.replaceFirst("\"", "'");
            String colName = col.substring(col.indexOf("'")+1, col.lastIndexOf("'"));
            String colValue = col.substring(col.indexOf("\"")+1, col.lastIndexOf("\""));
            dataColumnStream.addColumn(colName, colValue);
        }
        Constants.logger().info("Transformed json to data stream: " + dataColumnStream.toString());
        return dataColumnStream;
    }

}
