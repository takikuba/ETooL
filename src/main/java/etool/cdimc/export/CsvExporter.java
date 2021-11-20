package etool.cdimc.export;

import etool.cdimc.components.ExporterFrame;
import etool.cdimc.models.Table;
import etool.cdimc.stream.DataColumnStream;

import java.io.DataOutputStream;
import java.io.File;

public class CsvExporter implements Exporter {

    @Override
    public void export(Table table, DataColumnStream data) {
        ExporterFrame exporterFrame = new ExporterFrame();
        File file = exporterFrame.getFolder();
    }

}
