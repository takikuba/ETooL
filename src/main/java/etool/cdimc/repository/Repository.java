package etool.cdimc.repository;

public class Repository {
    private String name;
    private String location;

    public Repository() {
    }

    public Repository(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString(){
        return "Repository " + name + '\n'
                + "Path: " + location + '\n';
    }
}
