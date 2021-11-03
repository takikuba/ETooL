package etool.cdimc.stream;

import org.json.JSONObject;
import java.util.Set;

public class DataExtractStream {
    private JSONObject json;
    private DataColumnStream dataColumnStream;

    public DataExtractStream(){}

    public DataExtractStream(String json) {
        this.json = new JSONObject(json);
        this.dataColumnStream = StreamTransformer.transformJsonToDataStream(json);
    }

    public JSONObject getData(){
        return json;
    }

    public void filter(Set<String> columns) {
        this.dataColumnStream = dataColumnStream.getFilteredDataStream(columns);
        System.out.println(dataColumnStream);
    }

    public Set<String> getColumns() {
        return dataColumnStream.getColumns();
    }

    public String toDataString() {
        return dataColumnStream.toString();
    }

    public String toString() {
        return json.toString();
    }
}
