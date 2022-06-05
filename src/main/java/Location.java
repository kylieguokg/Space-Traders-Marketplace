import java.util.ArrayList;
import java.util.List;

public class Location {


    private boolean allowsConstruction;
    private String name;
    private String symbol;
    private String type;
    private Integer x;
    private Integer y;
    private List<String> traits;
    private List<String> messages;

    public Location(boolean allowsConstruction, String name, String symbol, String type, Integer x, Integer y, List<String> traits, List<String> messages){
        this.allowsConstruction = allowsConstruction;
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.x = x;
        this.y = y;
        this.traits = traits;
        this.messages = messages;
    }

    public List<String> getTraits(){
        List<String> updated = new ArrayList<String>();
        for (String trait : traits){
            updated.add(trait.replace("_"," "));
        }
        return updated;
    }



    public boolean isAllowsConstruction() {
        return allowsConstruction;
    }

    public void setAllowsConstruction(boolean allowsConstruction) {
        this.allowsConstruction = allowsConstruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String toString(){
        return "Name: " + name + " | Symbol: " + symbol
                + "\nType: " + type + " | Allows Construction: " + allowsConstruction
                + "\nx: " + x + " | y:" + y + "\n";

    }


    public List<String> getMessages() {
        List<String> updated = new ArrayList<String>();
        if (messages != null){
            for (String message : messages){

                String newMessage = "";

                int spaceCount = 0;

                for (int i = 0; i < message.length(); i++){
                    if (message.toCharArray()[i] == ' '){
                        spaceCount += 1;
                        if (spaceCount % 4 == 0){
                            newMessage += "\n";
                        }
                    }
                    newMessage += message.toCharArray()[i];
                }
                updated.add(newMessage);
            }
        }
        return updated;


    }
}
