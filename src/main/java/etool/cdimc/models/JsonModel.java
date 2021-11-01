package etool.cdimc.models;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonModel {
    private String data;
    private JSONObject json;


    public JsonModel(File file){
        try {
            data = Files.readString(Path.of(file.getPath()));
            json = new JSONObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return data;
    }
}
