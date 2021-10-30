package etool.cdimc.models;

import etool.cdimc.utils.BaseItem;
import etool.cdimc.utils.BaseModelItem;
import etool.cdimc.utils.ChildMapper;

import java.util.*;
import java.util.function.BiFunction;

public class SingleChildItem<T extends BaseItem> extends BaseModelItem {
    private final ChildMapper<T> childMapper;

    public SingleChildItem(String name, BaseModelItem parent) {
        super(name, parent);
        childMapper = new ChildMapper<>(null);
    }

    protected final T getOrCreateChild(String childName, BiFunction<String, BaseModelItem, T> childCreator) {
        return childMapper.getOrCreateChild(childName, this, childCreator);
    }

    public Iterator<BaseItem> iterator() {
        return childMapper.iterator();
    }

    public Collection<T> getChildren() {
        return childMapper.getChildren();
    }

    public Set<String> getChildrenNames() {
        return childMapper.getChildrenNames();
    }

    public void removeChild(BaseItem item) {
        childMapper.removeChildItem(item);
    }


}
