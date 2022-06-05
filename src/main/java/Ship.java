import java.util.List;
import java.util.ArrayList;
public class Ship {

    private List<Cargo> cargo = new ArrayList<Cargo>();
    private String shipClass;
    private String id;
    private PurchaseLocation purchaseLocation;
    private String location;
    private List<PurchaseLocation> purchaseLocations = new ArrayList<>();
    private String manufacturer;
    private Integer maxCargo;
    private Integer plating;
    private Integer speed;
    private Integer loadingSpeed;
    private List<String> restrictedGoods = new ArrayList<String>();
    private String type;
    private Integer weapons;
    private Integer spaceAvailable;
    private String flightPlanId;
    private Integer x;
    private Integer y;
    private boolean available = false;



    public Ship(String shipClass, String manufacturer,
                Integer maxCargo, Integer plating,
                Integer speed, Integer loadingSpeed, List<String> restrictedGoods,
                String type,
                Integer weapons, PurchaseLocation purchaseLocation,
                List<Cargo> cargo, String id,
                String location, Integer spaceAvailable,
                Integer x, Integer y){

        this.shipClass = shipClass;
        this.manufacturer = manufacturer;
        this.maxCargo = maxCargo;
        this.plating = plating;
        this.speed = speed;
        this.type = type;
        this.weapons = weapons;
        this.purchaseLocations.add(purchaseLocation);
        this.cargo = cargo;
        this.shipClass = shipClass;
        this.location = location;
        this.id = id;
        this.manufacturer = manufacturer;
        this.maxCargo = maxCargo;
        this.plating = plating;
        this.speed = speed;
        this.loadingSpeed = loadingSpeed;
        this.restrictedGoods = restrictedGoods;
        this.type = type;
        this.weapons = weapons;
        this.x = x;
        this.spaceAvailable = spaceAvailable;
        this.y = y;


    }


    public Integer getLoadingSpeed() {
        return loadingSpeed;
    }

    public List<String> getRestrictedGoods() {
        return restrictedGoods;
    }


    public PurchaseLocation getPurchaseLocation() {
        return purchaseLocation;
    }

    public Integer getSpaceAvailable() {
        return spaceAvailable;
    }

    public String getFlightPlanId() {
        return flightPlanId;
    }

    public void setFlightPlanId(String flightPlanId) {
        this.flightPlanId = flightPlanId;
    }



    public void newPurchaseLocations(){
        purchaseLocations = new ArrayList<PurchaseLocation>();
    }


    public void addLocation(PurchaseLocation location){
        purchaseLocations.add(location);
    }


    public List<PurchaseLocation> getPurchaseLocations() {
        return purchaseLocations;
    }

    public List<Cargo> getCargo() {
        return cargo;
    }

    public String getShipClass() {
        return shipClass;
    }

    public String getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getMaxCargo() {
        return maxCargo;
    }

    public Integer getPlating() {
        return plating;
    }

    public Integer getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public Integer getWeapons() {
        return weapons;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean a){
        this.available = a;
    }

    public void setShipClass(String shipClass){
        this.shipClass = shipClass;
    }

    public void addCargo(Cargo cargo){
        if (this.cargo == null){
            this.cargo = new ArrayList<Cargo>();
        }
        this.cargo.add(cargo);
    }

    public void removeCargo(String good, Integer quantity){
        for (Cargo cargo1 : cargo){
            if (cargo1.getGood().equals(good)){
                if ((cargo1.getQuantity() - quantity) <= 0){
                    cargo.remove(cargo1);
                } else {
                    cargo1.setQuantity(cargo1.getQuantity() - quantity);
                    cargo1.setTotalVolume(cargo1.getTotalVolume() - quantity);

                }
                break;
            }
        }
    }

    @Override
    public String toString() {

        String out = "ID: " + id + "\n\nCargo: ";

        if (cargo == null || cargo.size() == 0){
            out += "No cargo\n";
        } else {
            out += "\n";
            for (Cargo cargo1: cargo){
                out += cargo1.toString();
                out +="\n";
            }
        }

        out += "Class: " + shipClass + " | Manufacturer: " + manufacturer
                + "\nMaximum Cargo: " + maxCargo + " | Plating: " +  plating + " | Speed: " +  speed
                + "\nType: " + type + " | Weapons: " +  weapons
                + "\nx: " + x + " | y: " + y + "\n";

        return out;


    }

    public String getLocation() {
        return location;
    }
}
