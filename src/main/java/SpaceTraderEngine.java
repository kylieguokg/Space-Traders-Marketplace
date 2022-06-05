import java.util.ArrayList;
import java.util.List;


public class SpaceTraderEngine {

    private boolean online;
    static SpaceTraderDummy spaceTradersDummy = new SpaceTraderDummy();
    static SpaceTraderHTTP spaceTradersHTTP = new SpaceTraderHTTP();

    private User user;
    private List<Loan> availableLoans = new ArrayList<Loan>();
    private List<ActiveLoan> activeLoans = new ArrayList<ActiveLoan>();
    private List<Ship> availableShips = new ArrayList<Ship>();
    private List<Ship> myShips = new ArrayList<Ship>();
    private List<Location> nearbyLocations = new ArrayList<Location>();
    private List<Good> marketPlace = new ArrayList<Good>();
    private List<FlightPlan> currentFlights = new ArrayList<FlightPlan>();
    private Ship updatedShip;


    public SpaceTraderEngine(boolean online){
        this.online = online;
        if (online == false){
            SpaceTraderDummy spaceTradersDummy = new SpaceTraderDummy();
        }
    }

    public String addNewUser(String username){
        if (!online){
            return spaceTradersDummy.addNewUser(username);
        } else {
            return spaceTradersHTTP.addNewUser(username);
        }

    }

    public String getUserInfo(){
        if (!online){
            return spaceTradersDummy.getUserInfo();
        } else {
            return spaceTradersHTTP.getUserInfo();
        }
    }


    public String login(String username, String token){
        if (!online){
            user =  spaceTradersDummy.login(username, token);
            return "true";
        } else {
            String out = spaceTradersHTTP.login(username, token);
            user = spaceTradersHTTP.getUser();
            return out;
        }

    }


    public  String isServerActive() {

        if (!online){
            return spaceTradersDummy.getStatus();
        } else {
            return spaceTradersHTTP.isServerActive();
        }

    }


    public String listAvailableLoans(){
        if (!online){
            availableLoans = spaceTradersDummy.getAvailableLoans();
            return "ok";
        } else {
            String out = spaceTradersHTTP.listAvailableLoans();
            availableLoans = spaceTradersHTTP.getAvailableLoans();
            return out;
        }

    }

    public List<Loan> getAvailableLoans(){
        return availableLoans;
    }

    public String obtainLoan(String type){
        if (!online){
            ActiveLoan loan = spaceTradersDummy.obtainLoan(type);
            String out = "Credits: " + user.getCredits() + "\n";
            out += loan.toString();
            return out;
        } else {
            return spaceTradersHTTP.obtainLoan(type);
        }

    }

    public String listActiveLoans(){
        if (!online){
            activeLoans = spaceTradersDummy.getActiveLoans();
            return "ok";
        } else {
            String out = spaceTradersHTTP.listActiveLoans();
            activeLoans = spaceTradersHTTP.getActiveLoans();
            return out;
        }

        
    }

    public List<ActiveLoan> getActiveLoans(){
        return  activeLoans;
    }


    public String listAvailableShips(){
        if (!online){
            availableShips = spaceTradersDummy.getAvailableShips();
            return "ok";
        } else {
            String out = spaceTradersHTTP.listAvailableShips();
            availableShips = spaceTradersHTTP.getAvailableShips();
            return out;
        }

    }

    public List<Ship> getListAvailableShips(){
        return availableShips;
    }


    public String purchaseShip(String location, String type){
        if (!online){
            Ship ship = spaceTradersDummy.purchaseShip(location, type);
            String out = "Purchase Completed  | ";
            out += "Credits: " + user.getCredits();
            out += "\n";
            out += ship.toString();
            return out;
        } else {
            return spaceTradersHTTP.purchaseShip(location, type);
        }

        
    }


    public String listMyShips(){
        if (!online){
            myShips = spaceTradersDummy.getMyShips();
            return "ok";
        } else {
            String out = spaceTradersHTTP.listMyShips();
            myShips = spaceTradersHTTP.getMyShips();
            return out;
        }
        
    }

    public List<Ship> getMyShips(){
        return myShips;
    }

    public String purchaseShipFuel(String shipId, Integer quantity ){
        if (!online){
            Order order = spaceTradersDummy.purchaseShipFuel(shipId, quantity);

            String out = "Purchase Completed  | Credits: " + user.getCredits() + "\n\n";

            out += order.toString() + "\n";

            out += "Please see above for updated ship details\n";

            return out;
        } else {
            return spaceTradersHTTP.purchaseShipFuel(shipId, quantity);
        }

        
    }


    public String viewLocalMarketPlace(String locationSymbol){
        if (!online){
            marketPlace = spaceTradersDummy.getMarketPlace();
            return "ok";
        } else {
            String out = spaceTradersHTTP.viewLocalMarketPlace(locationSymbol);
            marketPlace = spaceTradersHTTP.getMarketPlace();
            return out;
        }

    }

    public List<Good> getMarketPlace(){
        return marketPlace;
    }


    public String getNearByLocations(){
        if (!online){
            nearbyLocations = spaceTradersDummy.getNearbyLocations();
            return "ok";
        } else {
            String out = spaceTradersHTTP.getNearByLocations();
            nearbyLocations = spaceTradersHTTP.getNearbyLocations();
            return out;
        }
        
    }

    public List<Location> getNearbyLocationsList(){
        return nearbyLocations;
    }

    public String purchaseGood(String shipId, String good, Integer quantity ){
        if (!online){
            Order order = spaceTradersDummy.purchaseGood(shipId, good, quantity);

            String out = "Purchase Completed  | Credits: " + user.getCredits() + "\n";

            out += order.toString();

            out += "\nPlease see below for updated ship details";

            updatedShip  =  spaceTradersDummy.getUpdatedShip();

            return out;
        } else {
            String out = spaceTradersHTTP.purchaseGood(shipId, good, quantity);
            updatedShip = spaceTradersHTTP.getUpdatedShip();
            return out;
        }

        
    }

    public Ship getUpdatedShip()
    {
        return updatedShip;
    }

    public String sellGood(String shipId, String good, Integer quantity ){
        if (!online){

            Order order = spaceTradersDummy.sellGood(shipId, good, quantity);

            String out = "Sale Completed | Credits: " + user.getCredits() +  " | ";

            out += order.toString() + "\n";

            out += "Please see above for updated ship details\n";

            updatedShip = spaceTradersDummy.getUpdatedShip();

            return out;

        } else {
            String out = spaceTradersHTTP.sellGood(shipId, good, quantity);
            updatedShip = spaceTradersHTTP.getUpdatedShip();
            return out;
        }


    }



    public String createFlightPlan(String shipId, String destination){
        if (!online){
            FlightPlan flightPlan = spaceTradersDummy.createFlightPlan(shipId, destination);
            updatedShip = spaceTradersDummy.getUpdatedShip();
            return flightPlan.toString();
        } else {
           String out = spaceTradersHTTP.createFlightPlan(shipId, destination);
           updatedShip = spaceTradersHTTP.getUpdatedShip();
           return out;
        }

        
    }



    public List<FlightPlan> getCurrentFlights(){

        if (!online){
            currentFlights = spaceTradersDummy.getCurrentFlights();
        } else {
            spaceTradersHTTP.findCurrentFlights();
            currentFlights = spaceTradersHTTP.getCurrentFlights();
        }

        return currentFlights;
    }


//    public String viewFlightPlan(String flightPlanId){
//        if (!online){
//            return "ok";
//        } else {
//            return spaceTradersHTTP.viewFlightPlan(flightPlanId);
//        }
//
//    }




}
