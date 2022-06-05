import java.time.Instant;


public class User {

    private Integer credits;
    private String joinedAt;
    private Integer shipCount;
    private Integer structureCount;
    private String username;
    private String token;

    public User(Integer credits, String joinedAt, Integer shipCount, Integer structureCount, String username, String token ) {
        this.credits = credits;
        this.joinedAt = joinedAt;
        this.shipCount = shipCount;
        this.structureCount = structureCount;
        this.username = username;
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getUsername(){
        return "Username: " + username;
    }

    public void addCredits(int c){
        credits += c;
    }

    public Integer getCredits(){
        return credits;
    }


    @Override
    public String toString() {
        return "Username: " + username + " | Credits: " + credits + "\nJoined At: " + joinedAt
                + "\nShip Count: " +  shipCount + " | Structure Count: " + structureCount;
    }
}
