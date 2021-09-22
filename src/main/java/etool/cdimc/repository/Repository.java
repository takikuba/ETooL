package etool.cdimc.repository;

public class Repository {
    private String name;
    private Vendor vendor;
    private String location;

    public Repository() {
    }

    public Repository(String name, Vendor vendor, String location) {
        this.name = name;
        this.vendor = vendor;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString(){
        return "Repository " + name + '\n'
                + "Type: " + vendor + '\n'
                + "Path: " + location + '\n';
    }
}
