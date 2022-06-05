import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

public class SpaceTraderDummy {

    static String username;
    static String token;
    private User user;
    private List<Loan> availableLoans = new ArrayList<Loan>();
    private List<ActiveLoan> activeLoans = new ArrayList<ActiveLoan>();
    private List<Ship> availableShips = new ArrayList<Ship>();
    private List<Ship> myShips = new ArrayList<Ship>();
    private List<Location> nearbyLocations = new ArrayList<Location>();
    private List<Good> marketPlace = new ArrayList<Good>();
    private List<FlightPlan> currentFlights = new ArrayList<FlightPlan>();
    private Ship updatedShip;


    public SpaceTraderDummy(){
        availableLoans.add(new Loan(100000, false, 10, 10, "STARTUP"));
        availableLoans.add(new Loan(200000, true, 27, 28, "STARTUP"));

        List<String> restrictedGoods = new ArrayList<String>();
        restrictedGoods.add("MILK");
        availableShips.add(new Ship("MK-III", "Zetra",300, 10, 2, 10, restrictedGoods, "ZA-MK-III", 10 , new PurchaseLocation("OE-PM-TR", 18400, "OE"), new ArrayList<Cargo>(), "ckm07ezq50354ti0j1drcey9v","OE-PM-TR", 10, 28, -3 ));
        List<Cargo> cargo = new ArrayList<Cargo>();
        cargo.add(new Cargo("FUEL", 1000, 1000));
        cargo.add(new Cargo("POTATOES", 1000, 1000));
        availableShips.add(new Ship("MK-II", "Jackshaw",100, 10, 2, 10, null, "JW-MK-II", 10, new PurchaseLocation("OE-NY", 23300, "OE"), cargo, "ckne4w8me01141ds62dnui0c8", "OE-NY", 29, 100, 23));
        availableShips.add(new Ship("MK-II", "Gravager",300, 10, 1, 10, null, "GR-MK-II", 5, new PurchaseLocation("OE-PM", 1222400, "OE"), new ArrayList<Cargo>(), "k232km07ez03fw54ti01drcey9v","OE-PM", 28, 30, 12 ));

        marketPlace.add(new Good("METAL", 2, 1000, 10, 100, 30, 10));
        marketPlace.add(new Good("CHEESE", 2, 28, 28, 28, 28, 10));
        marketPlace.add(new Good("FUEL", 2, 1000, 1, 20, 29, 10));

        List<String> traits = new ArrayList<String>();
        traits.add("NATURAL_CHEMICALS");
        traits.add("ARABLE_LAND");
        nearbyLocations.add(new Location(true,"Prime", "OE-PM", "PLANET", 20, -25,  traits, null ));
        nearbyLocations.add(new Location(true,"Ucarro", "OE-UC", "PLANET", -75, -82, traits, null ));
    }

    public String getStatus(){
        return "spacetraders is currently online and available to play";
    }

    public String addNewUser(String username){
        return "Your token is\n635ad0f0-c4cc-4da1-88b1-f579ef7387c9";
    }

    public User login(String username, String token){
        this.username = username;
        this.token = token;
        user = new User(0, java.time.Clock.systemUTC().instant().toString(), 0, 0, username, token);
        return user;
    }

    public String getUserInfo(){
        return user.toString();
    }

    public static String getUsername() {
        return username;
    }

    public static String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public List<Loan> getAvailableLoans() {
        return availableLoans;
    }

    public List<ActiveLoan> getActiveLoans() {
        return activeLoans;
    }

    public List<Ship> getAvailableShips() {
        return availableShips;
    }

    public List<Ship> getMyShips() {
        return myShips;
    }

    public List<Location> getNearbyLocations() {
        return nearbyLocations;
    }

    public List<Good> getMarketPlace() {
        return marketPlace;
    }

    public List<FlightPlan> getCurrentFlights() {
        return currentFlights;
    }

    public Ship getUpdatedShip() {
        return updatedShip;
    }


    public ActiveLoan obtainLoan(String type){
        user.addCredits(28000);
        ActiveLoan newLoan = new ActiveLoan("2023-05-15T02:32:43.269Z","ckoma153c0060zbnzquw2xa29", 28000, ActiveLoan.Status.CURRENT, "STARTUP" );
        activeLoans.add(newLoan);
        return newLoan;
    }

    public Ship purchaseShip(String location, String type){

        for (Ship ship: availableShips){
            if (ship.getLocation().equals(location)){
                myShips.add(ship);
                user.addCredits(-ship.getPurchaseLocations().get(0).getPrice());
                return ship;
            }
        }

        return null;

    }

    public Order purchaseShipFuel(String shipId, Integer quantity ){

        for (Ship ship: myShips){
            if (ship.getId().equals(shipId)){
                Order order = new Order("FUEL", 2, quantity, 2*quantity);
                user.addCredits(-2*quantity);
                ship.addCargo(new Cargo("FUEL", quantity, quantity));
                return order;
            }
        }

        return null;

    }

    public Order purchaseGood(String shipId, String good, Integer quantity){
        for (Ship ship: myShips){
            if (ship.getId().equals(shipId)){
                Order order = new Order(good, 2, quantity, 2*quantity);
                user.addCredits(-2*quantity);
                ship.addCargo(new Cargo(good, quantity, quantity));
                updatedShip = ship;
                return order;
            }
        }

        return null;
    }

    public Order sellGood(String shipId, String good, Integer quantity){
        for (Ship ship: myShips){
            if (ship.getId().equals(shipId)){
                Order order = new Order(good, 10, quantity, 10*quantity);
                user.addCredits(10*quantity);
                ship.removeCargo(good, quantity);
                updatedShip = ship;
                return order;
            }
        }

        return null;
    }

    public FlightPlan createFlightPlan(String shipId, String destination){
        for (Ship ship: myShips){
            if (ship.getId().equals(shipId)){
                ship.setFlightPlanId("ckm07t6ki0038060jv7b2x5gk");
            }
        }

        FlightPlan flightPlan = new FlightPlan("2023-03-08T06:41:19.658Z","2022-03-08T06:41:19.658Z", "OE-PM-TR", destination, 1, 1, 19, "ckm07t6ki0038060jv7b2x5gk", shipId,  null, 67);
        currentFlights.add(flightPlan);
        return flightPlan;
    }


}
