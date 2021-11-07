package etool.cdimc.stream;

import etool.cdimc.Constants;
import etool.cdimc.db.DbFile;

import java.io.StringWriter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataColumnStream {

    private Logger logger = Constants.logger();
    private final HashMap<String, ArrayList<String>> columnDataMap = new HashMap<>();
    private String name;
    private String exampleColumn;

    public DataColumnStream(){};

    public DataColumnStream(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFirstColumn() {
        return (String) getColumns().toArray()[0];
    }

    public int getColumnValuesSize() {
        return columnDataMap.get(exampleColumn).size();
    }

    public int getColumnSize() {
        return columnDataMap.size();
    }

    public String getIndexColumnValue(String column, int index) {
        return columnDataMap.get(column).get(index);
    }

    public void addColumn(String column) {
        this.exampleColumn = column;
        columnDataMap.put(column, new ArrayList<>());
    }

    public void addColumn(String column, String value) {
        if(!columnDataMap.containsKey(column)){
            columnDataMap.put(column, new ArrayList<>());
        }
        this.exampleColumn = column;
        addColumnValue(column,value);
    }

    public void addColumn(String column, ArrayList<String> values) {
        columnDataMap.put(column, new ArrayList<>());
        this.exampleColumn = column;
        addColumnValues(column, values);
    }

    public void addColumnValue(String column, String value) {
        columnDataMap.get(column).add(value);
        this.exampleColumn = column;
    }

    public void addColumnValues(String column, ArrayList<String> values) {
        values.forEach(value -> addColumnValue(column, value));
    }

    public Set<String> getColumns() {
        return columnDataMap.keySet();
    }

    public ArrayList<String> getColumnValues(String column) {
        return columnDataMap.get(column);
    }

    public DataColumnStream getFilteredDataStream(Set<String> columns) {
        logger.info("Filtering data!");
        DataColumnStream dcs = new DataColumnStream(this.name);
        for (String col : columns) {
            if (columnDataMap.containsKey(col)) {
                dcs.addColumn(col, columnDataMap.get(col));
            } else {
                logger.warning("Column " + col + " don't exist! Will not be filtered!");
            }
        }
        System.out.println(dcs.getWriter());
        return dcs;
    }

    public String getWriter() {
        return DbFile.getOutputFormat(this);
    }

    private void newColumn(String column) {
        this.exampleColumn = column;
        if(!columnDataMap.containsKey(column)){
            columnDataMap.put(column, new ArrayList<>());
        }
    }

    @Override
    public String toString(){
        return columnDataMap.toString();
    }
}
