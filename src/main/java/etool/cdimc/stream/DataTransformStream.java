package etool.cdimc.stream;

import java.io.StringWriter;

public class DataTransformStream {
    private StringWriter writer;

    public DataTransformStream(){}

    public DataTransformStream(StringWriter writer) {
        this.writer = writer;
    }

    public StringWriter getWriter() {
        return writer;
    }

}
