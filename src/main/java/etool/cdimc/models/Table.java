package etool.cdimc.models;

import etool.cdimc.repository.Repository;
import etool.cdimc.repository.Vendor;

import java.util.Set;
import java.util.stream.Collectors;

public class Table {
    private final String orgName;
    private String name;
    private Set<String> columns;
    private static int index = 1;

    public Table(String name, Set<String> columns){
        this.orgName = name;
        this.name = name;
        this.columns = columns;
    }

    public Table changeName() {
        name = orgName + index++;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return name + "." + "cef";
    }

    public String getTableWriter() {
        Set<String> newColumns = columns.stream().map(str -> ";column." + str).collect(Collectors.toSet());
        return name + String.join("", newColumns) + ';';
    }

    public Set<String> getColumns() {
        return columns;
    }

    public void setColumns(Set<String> columns) {
        this.columns = columns;
    }
}
