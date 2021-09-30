package etool.cdimc.etl.extractors;

import etool.cdimc.Constants;
import etool.cdimc.stream.DataExtractStream;

import java.io.File;
import java.util.logging.Logger;

public interface Extractor {
    Logger logger = Constants.logger();

    DataExtractStream extract(File data);
}
