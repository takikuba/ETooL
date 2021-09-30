package etool.cdimc.etl.transformers;

import etool.cdimc.Constants;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;

import java.util.logging.Logger;

public interface Transformer {
    Logger logger = Constants.logger();

    DataTransformStream transform(DataExtractStream data);
}
