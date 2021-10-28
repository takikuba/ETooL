package etool.cdimc.models;

import java.util.ArrayList;

public class RelationalModel {

    static public class DataBase extends SingleChildItem implements BaseItem {
        private final String name;

        public DataBase(String name){
            super(null);
            this.name = name;
        }

        public void getOrCreateSchema(String name) {
            getOrCreateChild(name);
        }

        @Override
        public String toString() {
            return "DataBase{" +
                    "items=" + getChildrenNames() +
                    '}';
        }

        @Override
        public String getName() {
            return name;
        }
    }

    static public class Schema extends SingleChildItem implements BaseItem {
        private String name;

        public Schema(BaseItem item, String name) {
            super(item);
            this.name = name;
        }

        public Table getOrCreateTable(String name) {
            return (Table) this.getOrCreateChild(name);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Schema{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static public class Table extends SingleChildItem implements BaseItem {
        private final String name;

        public Table(BaseItem parent, String name){
            super(parent);
            this.name = name;
        }

        public Column getOrCreateColumn(String name) {
            return (Column) this.getOrCreateChild(name);
        }

        @Override
        public String getName() {
            return name;
        }
    }

    static public class Column implements BaseItem {
        private final String name;
        private final BaseItem parent;

        public Column(BaseItem parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

}
