package etool.cdimc.models;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleChildItem {
    private final ArrayList<BaseItem> children = new ArrayList<>();

    public SingleChildItem(BaseItem item) {
        children.add(item);
    }

    public SingleChildItem(String name) {
        children.add(new BaseItem() {
            @Override
            public String getName() {
                return name;
            }
        });
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

        children.add(item);
        return item;
    }

    public ArrayList<BaseItem> getChildren() {
        return children;
    }
}
