package etool.cdimc.models;

import etool.cdimc.utils.BaseModelItem;
import etool.cdimc.utils.ChildMapper;

public class RelationalModel {

    static public class DataBase extends SingleChildItem<Schema> {

        public DataBase(String name, BaseModelItem parent){
            super(name, parent);
        }

        public Schema getOrCreateSchema(String name) {
            return getOrCreateChild(name, Schema::new);
        }
    }

    static public class Schema extends SingleChildItem<Table> {
        private ChildMapper<Table> childMapper;

        public Schema(String name, BaseModelItem parent) {
            super(name, parent);
        }

        public Table getOrCreateTable(String name) {
            return getOrCreateChild(name, Table::new);
        }
    }

    static public class Table extends SingleChildItem<Column> {
        private ChildMapper<Column> childMapper;

        public Table(String name, BaseModelItem parent){
            super(name, parent);
        }

        public Column getOrCreateColumn(String name) {
            return getOrCreateChild(name, Column::new);
        }
    }

    static public class Column extends BaseModelItem {

        public Column(String name, BaseModelItem parent) {
            super(name, parent);
        }
    }

}
