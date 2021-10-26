package etool.cdimc.models;

public interface BaseItem {
    String name = "";

    default String getName() {
        return name;
    }
}
