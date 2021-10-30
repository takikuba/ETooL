package etool.cdimc.utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ChildMapper<T extends BaseItem> implements Iterable<BaseItem> {

    private final Map<String, T> items = new LinkedHashMap<>();
    private final String context;

    public ChildMapper(String context){
        this.context = context;
    }

    public final T getOrCreateChild(String childName, BaseModelItem parent, BiFunction<String, BaseModelItem, T> childCreator) {
        return getOrCreateChild(childName, () -> childCreator.apply(childName, parent));
    }

    public final T getOrCreateChild(String childName, Supplier<T> childCreator) {
        return items.computeIfAbsent(childName, (key) -> childCreator.get());
    }

    public final T getChild(String childName) {
        return items.get(childName);
    }

    public int getSize() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public Iterator<BaseItem> iterator() {
        return (Iterator<BaseItem>) items.values().iterator();
    }

    public Collection<T> getChildren() {
        return items.values();
    }

    public Set<String> getChildrenNames() {
        return items.keySet();
    }

    public void removeChildItem(BaseItem child) {
        HashSet<String> keys = new HashSet<>(items.keySet());
        keys.stream().filter(k -> child == items.get(k)).forEach(items::remove);
    }
}
