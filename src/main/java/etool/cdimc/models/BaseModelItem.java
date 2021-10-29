package etool.cdimc.models;

public class BaseModelItem implements BaseItem {

    private final String name;
    private final BaseModelItem parent;

    public BaseModelItem(String name, BaseModelItem parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    public BaseModelItem getParent() {
        return parent;
    }
}
