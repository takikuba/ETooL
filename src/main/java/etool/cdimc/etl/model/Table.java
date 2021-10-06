package etool.cdimc.etl.model;

import etool.cdimc.repository.Repository;
import etool.cdimc.repository.Vendor;

import java.util.Set;
import java.util.stream.Collectors;

public class Table {
    private final String orgName;
    private String name;
    private final Set<String> rows;
    private final Vendor vendor;
    private static int index = 1;

    public Table(String name, Set<String> rows, Repository repository){
        this.orgName = name;
        this.name = name;
        this.rows = rows;
        this.vendor = repository.getVendor();
    }

    public Table changeName() {
        name = orgName + index++;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return name + "." + vendor.name().toLowerCase();
    }

    public String getTableWriter() {
        Set<String> newRows = rows.stream().map(str -> ";row." + str).collect(Collectors.toSet());
        return name + String.join("", newRows) + ';';
    }
}
