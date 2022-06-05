public class PurchaseLocation {

    private String location;
    private Integer price;
    private String system;

    public PurchaseLocation(String location, Integer price, String system) {
        this.location = location;
        this.price = price;
        this.system = system;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }


    @Override
    public String toString(){
        return "Location: " + location + " | Price: " + price
                + "\nSystem: " + system + "\n";
    }



}
