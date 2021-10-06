package etool.cdimc.stream;

import com.google.gson.Gson;

import java.io.StringWriter;

public class DataTransformStream {
    private StringWriter writer;

    public DataTransformStream(){}

    public DataTransformStream(StringWriter writer) {
        this.writer = writer;
    }

    public DataTransformStream(String writer) {
        StringWriter sw = new StringWriter();
        sw.write(writer);
        this.writer = sw;
    }

    public DataTransformStream(Gson gson) {
        StringWriter writer = new StringWriter();
        writer.write(gson.toJson(gson));
        this.writer = writer;
    }

    public StringWriter getWriter() {
        return writer;
    }

}
