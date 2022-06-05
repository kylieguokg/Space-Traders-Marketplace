import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SpaceTraderHTTP {

    static Gson gson = new Gson();
    static JsonParser jsonParser = new JsonParser();
    private User user;

    private List<Loan> availableLoans = new ArrayList<Loan>();
    private List<ActiveLoan> activeLoans = new ArrayList<ActiveLoan>();
    private List<Ship> availableShips = new ArrayList<Ship>();
    private List<Ship> myShips = new ArrayList<Ship>();
    private List<Location> nearbyLocations = new ArrayList<Location>();
    private List<Good> marketPlace = new ArrayList<Good>();
    private List<FlightPlan> currentFlights = new ArrayList<FlightPlan>();
    private Ship updatedShip;


    public String addNewUser(String username){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/users/" + username +"/claim"))
                    .POST(HttpRequest.BodyPublishers.ofString(username))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement tokenJE = jsonParser.parse(response.body());
            if (tokenJE.getAsJsonObject().get("error") != null){

                String errorCode = tokenJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("40901")){
                    return "ERROR: Username has already been claimed.";
                } else if (errorCode.equals("404")){
                    return "ERROR: Please enter a username";
                }

                return "ERROR: " + tokenJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            String token = tokenJE.getAsJsonObject().get("token").getAsString();

            return "Your token is\n" + token.toString();

        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went\nwrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "Username is invalid";
        }
    }

    public String getUserInfo(){

        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/account"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//             spaceTradersHTTP.viewFlightPlan(flightPlanId);spa   System.out.println("Response body was:\n" + response.body());

            JsonElement userJE = jsonParser.parse(response.body());

            if (userJE.getAsJsonObject().get("error") != null){
                String errorCode = userJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("40101")){
                    return "ERROR: Invalid token.";
                }
                return "ERROR: " + userJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            User user = gson.fromJson(userJE.getAsJsonObject().get("user"), User.class);

            return user.toString();


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is incorrect";
        }




    }

    public String login(String username, String token){
        try {

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/account/"))
                    .GET()
                    .header("Authorization", "Bearer " + token)
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement loginJE = jsonParser.parse(response.body());


            if (loginJE.getAsJsonObject().get("error") != null){

                String errorCode = loginJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("40101")){
                    return "ERROR: Invalid token.";
                }

                return "ERROR: " + loginJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            JsonObject login = loginJE.getAsJsonObject().get("user").getAsJsonObject();

            user = gson.fromJson(login, User.class);
            user.setToken(token);

            return "true";

        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went\nwrong with our request!";
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }


    public String isServerActive(){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/game/status"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());


            JsonElement statusJE = jsonParser.parse(response.body());

            if (statusJE.getAsJsonObject().get("error") != null){

                return "ERROR: " + statusJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            String status = statusJE.getAsJsonObject().get("status").getAsString();

            return status;

        } catch (IOException | InterruptedException e) {
            return "ERROR: Server is not active";
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }

    public String listAvailableLoans(){
        try {

            if (user == null){
                return "User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/types/loans"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement loansJE = jsonParser.parse(response.body());

            if (loansJE.getAsJsonObject().get("error") != null){
                String errorCode = loansJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();


                return "ERROR: " + loansJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            JsonArray loansArray = loansJE.getAsJsonObject().get("loans").getAsJsonArray();

            int i = 0;


            availableLoans = new ArrayList<Loan>();

            while (i < loansArray.size()){
                Loan loan = gson.fromJson(loansArray.get(i), Loan.class);
                availableLoans.add(loan);
                i += 1;

            }


            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }

    public String obtainLoan(String type){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/loans?type=" + type))
                    .POST(HttpRequest.BodyPublishers.ofString("type=" + type))
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement loanJE = jsonParser.parse(response.body());

            if (loanJE.getAsJsonObject().get("error") != null){
                String errorCode = loanJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("422")){
                    return "ERROR: Only one loan allowed at a time.";
                }

                return "ERROR: " + loanJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            String out = "Credits: " + gson.fromJson(loanJE.getAsJsonObject().get("credits"), String.class);
            out += "\n";

            ActiveLoan loan = gson.fromJson(loanJE.getAsJsonObject().get("loan"), ActiveLoan.class);
            out += loan.toString();

            return out;


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";



        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }

    public String listActiveLoans(){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/loans"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement loansJE = jsonParser.parse(response.body());

            if (loansJE.getAsJsonObject().get("error") != null){
                String errorCode = loansJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                return "ERROR: " + loansJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();


            }


            JsonArray loansArray = loansJE.getAsJsonObject().get("loans").getAsJsonArray();

            int i = 0;


            activeLoans = new ArrayList<ActiveLoan>();

            if (loansArray.size() == 0){
                return "You have no active loans";
            }

            while (i < loansArray.size()){
                ActiveLoan loan = gson.fromJson(loansArray.get(i), ActiveLoan.class);
                activeLoans.add(loan);

                i += 1;

            }

            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";



        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }

    public String listAvailableShips(){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/systems/OE/ship-listings"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement shipsJE = jsonParser.parse(response.body());

            if (shipsJE.getAsJsonObject().get("error") != null){
                String errorCode = shipsJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                return "ERROR: " + shipsJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            JsonArray shipsArray = shipsJE.getAsJsonObject().get("shipListings").getAsJsonArray();

            availableShips = new ArrayList<Ship>();
            int i = 0;



            while (i < shipsArray.size()){
                Ship ship = gson.fromJson(shipsArray.get(i), Ship.class);
                ship.setShipClass(shipsArray.get(i).getAsJsonObject().get("class").getAsString());
                availableShips.add(ship);
                i += 1;

            }


            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }

    public String purchaseShip(String location, String type){

        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/ships?location=" + location + "&type=" + type ))
                    .POST(HttpRequest.BodyPublishers.ofString("location=" + location + "&type=" + type))
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement shipJE = jsonParser.parse(response.body());

            if (shipJE.getAsJsonObject().get("error") != null){
                String errorCode = shipJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("400")){
                    return "ERROR: User has insufficient funds to purchase ship.";
                } else if (errorCode.equals("42201")){
                    return "ERROR: Please select a location";
                } else if (errorCode.equals("4")){
                    return "ERROR: You need to have a ship at the point of purchase when buying a new ship.";
                }

                return "ERROR: " + shipJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            Ship ship = gson.fromJson(shipJE.getAsJsonObject().get("ship"), Ship.class);


            String out = "Purchase Completed  | ";
            out += "Credits: " + shipJE.getAsJsonObject().get("credits").getAsString();



            ship.setShipClass(shipJE.getAsJsonObject().get("ship").getAsJsonObject().get("class").getAsString());

            out += "\n";
            out += ship.toString();

            return out;

        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }
    }

    public String listMyShips(){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/ships"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement shipsJE = jsonParser.parse(response.body());

            if (shipsJE.getAsJsonObject().get("error") != null){
                String errorCode = shipsJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                return "ERROR: " + shipsJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            JsonArray shipsArray = shipsJE.getAsJsonObject().get("ships").getAsJsonArray();

            int i = 0;


            myShips = new ArrayList<Ship>();

            if (shipsArray.size() == 0){
                return "You have no ships";
            }

            while (i < shipsArray.size()){
                Ship ship = gson.fromJson(shipsArray.get(i), Ship.class);
                ship.setShipClass(shipsArray.get(i).getAsJsonObject().get("class").getAsString());
                myShips.add(ship);
                i += 1;
            }


            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }

    }


    public String purchaseShipFuel(String shipId, Integer quantity ){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/purchase-orders?shipId=" + shipId + "&good=" + "FUEL" +  "&quantity=" + quantity))
                    .POST(HttpRequest.BodyPublishers.ofString("shipId=" + shipId + "&good=" + "FUEL" +  "&quantity=" + quantity))
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement shipFuelJE = jsonParser.parse(response.body());

            if (shipFuelJE.getAsJsonObject().get("error") != null){
                String errorCode = shipFuelJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("42201")){
                    return "ERROR: The quantity must be at least 1.";
                }

                return "ERROR: " + shipFuelJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            Order order = gson.fromJson(shipFuelJE.getAsJsonObject().get("order"), Order.class);

            String out = "Purchase Completed  | Credits: " + shipFuelJE.getAsJsonObject().get("credits").getAsString() + "\n\n";

            out += order.toString() + "\n";

            Ship ship = gson.fromJson(shipFuelJE.getAsJsonObject().get("ship"), Ship.class);

            out += "Please see above for updated ship details\n";

            return out;


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }

    }

    public String viewLocalMarketPlace(String locationSymbol){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/locations/" + locationSymbol + "/marketplace"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement marketPlaceJE = jsonParser.parse(response.body());

            if (marketPlaceJE.getAsJsonObject().get("error") != null){
                String errorCode = marketPlaceJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();


                return "ERROR: " + marketPlaceJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            JsonArray goodsArray = marketPlaceJE.getAsJsonObject().get("marketplace").getAsJsonArray();

            int i = 0;
            marketPlace = new ArrayList<Good>();

            while (i < goodsArray.size()){
                Good good = gson.fromJson(goodsArray.get(i), Good.class);
                marketPlace.add(good);
                i += 1;
            }

            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }

    }

    public String getNearByLocations(){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/systems/OE/locations"))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());
////
            JsonElement locationsJE = jsonParser.parse(response.body());

            if (locationsJE.getAsJsonObject().get("error") != null){
                String errorCode = locationsJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("400")){
                    return "ERROR: User has insufficient funds to purchase ship.";
                }

                return "ERROR: " + locationsJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            JsonArray locationsArray = locationsJE.getAsJsonObject().get("locations").getAsJsonArray();

            int i = 0;

            nearbyLocations = new ArrayList<Location>();

            while (i < locationsArray.size()){
                Location location = gson.fromJson(locationsArray.get(i), Location.class);
                nearbyLocations.add(location);
                i += 1;
            }

            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }

    }

    public String purchaseGood(String shipId, String good, Integer quantity ){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/purchase-orders?shipId=" + shipId + "&good=" + good +  "&quantity=" + quantity))
                    .POST(HttpRequest.BodyPublishers.ofString("shipId=" + shipId + "&good=" + good +  "&quantity=" + quantity))
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement goodJE = jsonParser.parse(response.body());

            if (goodJE.getAsJsonObject().get("error") != null){
                String errorCode = goodJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();


                return "ERROR: " + goodJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            Order order = gson.fromJson(goodJE.getAsJsonObject().get("order"), Order.class);

            String out = "Purchase Completed  | Credits: " + goodJE.getAsJsonObject().get("credits").getAsString() + " | ";

            out += order.toString();

            out += "\nPlease see below for updated ship details";

            updatedShip  = gson.fromJson(goodJE.getAsJsonObject().get("ship"), Ship.class);
            updatedShip.setShipClass(goodJE.getAsJsonObject().get("ship").getAsJsonObject().get("class").getAsString());

            return out;


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";

        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }

    }

    public String sellGood(String shipId, String good, Integer quantity ){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/sell-orders?shipId=" + shipId + "&good=" + good +  "&quantity=" + quantity))
                    .POST(HttpRequest.BodyPublishers.ofString("shipId=" + shipId + "&good=" + good +  "&quantity=" + quantity))
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement goodJE = jsonParser.parse(response.body());

            if (goodJE.getAsJsonObject().get("error") != null){
                String errorCode = goodJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();

                if (errorCode.equals("42201")){
                    return "ERROR: The quantity must be at least 1.";
                }

                return "ERROR: " + goodJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }


            Order order = gson.fromJson(goodJE.getAsJsonObject().get("order"), Order.class);

            String out = "Sale Completed | Credits: " + goodJE.getAsJsonObject().get("credits").getAsString() +  " | ";

            out += order.toString() + "\n";

            out += "Please see above for updated ship details\n";

            return out;


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";



        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.

            return "ERROR: Invalid URI";
        }

    }

    public String createFlightPlan(String shipId, String destination){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/flight-plans?shipId=" + shipId + "&destination=" + destination))
                    .POST(HttpRequest.BodyPublishers.ofString("shipId=" + shipId +  "&destination=" + destination))
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());

            JsonElement flighPlanJE = jsonParser.parse(response.body());

            if (flighPlanJE.getAsJsonObject().get("error") != null){
                String errorCode = flighPlanJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();


                return "ERROR: " + flighPlanJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            FlightPlan flightPlan = gson.fromJson(flighPlanJE.getAsJsonObject().get("flightPlan"), FlightPlan.class);

            return flightPlan.toString();


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";



        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }


    }


    public String viewFlightPlan(String flightPlanId){
        try {

            if (user == null){
                return "ERROR: User needs to be logged in";
            }

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/flight-plans/" + flightPlanId))
                    .GET()
                    .header("Authorization", "Bearer " + user.getToken() )
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//                System.out.println("Response status code was: " + response.statusCode());
//                System.out.println("Response headers were: " + response.headers());
//                System.out.println("Response body was:\n" + response.body());
            JsonElement flighPlanJE = jsonParser.parse(response.body());

            if (flighPlanJE.getAsJsonObject().get("error") != null){
                String errorCode = flighPlanJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("code").getAsString();


                return "ERROR: " + flighPlanJE.getAsJsonObject().get("error")
                        .getAsJsonObject().get("message").getAsString();

            }

            FlightPlan flightPlan = gson.fromJson(flighPlanJE.getAsJsonObject().get("flightPlan"), FlightPlan.class);

            currentFlights.add(flightPlan);

            return "ok";


        } catch (IOException | InterruptedException e) {
            return "ERROR: Something went wrong with our request!";



        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            return "ERROR: URI is invalid";
        }

    }

    public List<String> getCurrentFlightPlanIds(){

        listMyShips();

        List<Ship> allMyShips = getMyShips();
        List<String> shipsFlyingId = new ArrayList<String>();

        for (Ship ship: allMyShips){
            if (ship.getFlightPlanId() != null){
                shipsFlyingId.add(ship.getFlightPlanId());
            }
        }

        return shipsFlyingId;

    }

    public String findCurrentFlights(){

        List<String> shipsFlyingId = getCurrentFlightPlanIds();

        currentFlights = new ArrayList<FlightPlan>();

        for (String flightPlanId: shipsFlyingId){
            if (viewFlightPlan(flightPlanId).startsWith("ERROR:")){
                return viewFlightPlan(flightPlanId);
            }
        }

        return "ok";

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
}
