package etool.cdimc.export;

import etool.cdimc.models.Table;
import etool.cdimc.stream.DataColumnStream;

public interface Exporter {
    void export(Table table, DataColumnStream data);
}
