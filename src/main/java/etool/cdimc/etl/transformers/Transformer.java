package etool.cdimc.etl.transformers;

import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;

public interface Transformer {
    public DataTransformStream transform(DataExtractStream data);
}
