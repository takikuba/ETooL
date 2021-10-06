package etool.cdimc.etl.transformers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;

public class TransformerJson implements Transformer {
    @Override
    public DataTransformStream transform(DataExtractStream data) {
        logger.info("Run!");

        JsonObject json = JsonParser.parseString(data.getData().toString()).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return new DataTransformStream(prettyJson);
    }
}
