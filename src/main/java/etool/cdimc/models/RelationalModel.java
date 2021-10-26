package etool.cdimc.models;

import java.sql.SQLClientInfoException;

public class RelationalModel {

    static public class DataBase extends SingleChildItem implements BaseItem {

        public DataBase(String name){
            super(name);
        }

        public Schema getOrCreateSchema(String name) {
            return (Schema) getOrCreateChild(name);
        }

    }

    static public class Schema extends SingleChildItem implements BaseItem {

        private Schema(BaseItem item) {
            super(item);
        }

        public Schema(String name) {
            super(name);
        }

        public Table getOrCreateTable(String name) {
            return (Table) this.getOrCreateChild(name);
        }
    }

    static public class Table extends SingleChildItem implements BaseItem {

        private Table(BaseItem item){
            super(item);
        }

        public Column getOrCreateColumn(String name) {
            return (Column) this.getOrCreateChild(name);
        }
    }

    static public class Column implements BaseItem {

    }

}
