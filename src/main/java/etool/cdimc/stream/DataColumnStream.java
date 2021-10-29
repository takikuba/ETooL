package etool.cdimc.stream;

import etool.cdimc.Constants;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataColumnStream {

    private Logger logger = Constants.logger();
    private HashMap<String, ArrayList<String>> columnDataMap = new HashMap<>();

    public DataColumnStream(){}

    public DataColumnStream(String column, ArrayList<String> values) {
        columnDataMap.put(column, values);
    }

    public void addColumn(String column) {
        columnDataMap.put(column, new ArrayList<>());
    }

    public void addColumn(String column, String value) {
        columnDataMap.put(column, new ArrayList<>());
        addColumnValue(column,value);
    }

    public void addColumn(String column, ArrayList<String> values) {
        columnDataMap.put(column, new ArrayList<>());
        addColumnValues(column, values);
    }

    public void addColumnValue(String column, String value) {
        columnDataMap.get(column).add(value);
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
        DataColumnStream dcs = new DataColumnStream();
        columns.forEach(col -> {
            if (columnDataMap.containsKey(col)) {
                dcs.addColumn(col, columnDataMap.get(col));
            } else {
                logger.warning("Column " + col + " don't exist! Will not be filtered!");
            }
        });
        return dcs;
    }

    @Override
    public String toString(){
        return columnDataMap.toString();
    }
}
