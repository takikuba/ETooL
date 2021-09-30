package etool.cdimc.etl.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.join;

public class Table {
    private String name;
    private Set<String> rows;

    public Table(String name, Set<String> rows){
        this.name = name;
        this.rows = rows;
    }

    public String getTableWriter(){
        Set<String> newRows = rows.stream().map(str -> ";row." + str).collect(Collectors.toSet());
        return name + String.join("", newRows) + ';';
    }
}
