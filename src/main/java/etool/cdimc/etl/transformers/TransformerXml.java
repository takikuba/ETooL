package etool.cdimc.etl.transformers;

import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;

public class TransformerXml implements Transformer {
    @Override
    public DataTransformStream transform(DataExtractStream data) {
        logger.info("Run!");

        return null;
    }
}
