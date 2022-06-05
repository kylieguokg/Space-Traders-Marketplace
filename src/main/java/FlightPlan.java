public class FlightPlan {

    private String arrivesAt;
    private String createdAt;
    private String departure;
    private String destination;
    private Integer distance;
    private Integer fuelConsumed;
    private Integer fuelRemaining;
    private String id;
    private String shipId;
    private String terminatedAt;
    private Integer timeRemainingInSeconds;

    public FlightPlan(String arrivesAt, String createdAt, String departure, String destination,
                      Integer distance, Integer fuelConsumed, Integer fuelRemaining,
                      String id, String shipId, String terminatedAt, Integer timeRemainingInSeconds){

        this.arrivesAt = arrivesAt;
        this.createdAt = createdAt;
        this.departure = departure;
        this.destination = destination;
        this.distance = distance;
        this.fuelConsumed = fuelConsumed;
        this.fuelRemaining = fuelRemaining;
        this.id = id;
        this.shipId = shipId;
        this.terminatedAt = terminatedAt;
        this.timeRemainingInSeconds = timeRemainingInSeconds;

    }

    public String getArrivesAt() {
        return arrivesAt;
    }

    public void setArrivesAt(String arrivesAt) {
        this.arrivesAt = arrivesAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getFuelConsumed() {
        return fuelConsumed;
    }

    public void setFuelConsumed(Integer fuelConsumed) {
        this.fuelConsumed = fuelConsumed;
    }

    public Integer getFuelRemaining() {
        return fuelRemaining;
    }

    public void setFuelRemaining(Integer fuelRemaining) {
        this.fuelRemaining = fuelRemaining;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getTerminatedAt() {
        return terminatedAt;
    }

    public void setTerminatedAt(String terminatedAt) {
        this.terminatedAt = terminatedAt;
    }

    public Integer getTimeRemainingInSeconds() {
        return timeRemainingInSeconds;
    }

    public void setTimeRemainingInSeconds(Integer timeRemainingInSeconds) {
        this.timeRemainingInSeconds = timeRemainingInSeconds;
    }

    public String toString(){
        String transit = terminatedAt;

        if (terminatedAt == null){
            transit = "In transit";
        }
        return "Flight Confirmed | Departure: " + departure + " | Destination: " + destination
                + "\nArrives At: " + arrivesAt + " | Created At: " + createdAt
                + "\nDistance: " + distance + " | Fuel Consumed: " + fuelConsumed + " | Fuel Remaining: " + fuelRemaining
                + "\nID: " + id  + " | Ship ID: " + shipId
                + "\nTerminated At: " + transit + " | Time Remaining in Seconds: " + timeRemainingInSeconds;
    }
}
