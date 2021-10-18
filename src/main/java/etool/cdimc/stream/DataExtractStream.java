package etool.cdimc.stream;

import org.json.JSONObject;

public class DataExtractStream {
    private JSONObject json;

    public DataExtractStream(){}

    public DataExtractStream(JSONObject json) {
        this.json = json;
    }

    public DataExtractStream(String json) {
        this.json = new JSONObject(json);
    }

    public JSONObject getData(){
        return json;
    }

}
