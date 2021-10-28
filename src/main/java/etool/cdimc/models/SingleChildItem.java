package etool.cdimc.models;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleChildItem {
    private final ArrayList<BaseItem> children = new ArrayList<>();
    private final ArrayList<String> items = new ArrayList<>();
    private BaseItem parent;

    public SingleChildItem(BaseItem parent) {
        this.parent = parent;
    }

    public BaseItem getOrCreateChild(String name){
        for(BaseItem item: children){
            if(item.getName().equals(name)){
                return item;
            }
        }
        BaseItem item = new BaseItem() {
            @Override
            public String getName() {
                return name;
            }
        };

        items.add(name);
        children.add(item);
        return item;
    }

    public ArrayList<BaseItem> getChildren() {
        return children;
    }

    public ArrayList<String> getChildrenNames() {
        return items;
    }
}
