import java.util.*;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.TableView.TableViewSelectionModel;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpaceTraderView  {

    private SpaceTraderEngine spaceTraderEngine;

    public SpaceTraderView(SpaceTraderEngine spaceTraderEngine){

        this.spaceTraderEngine = spaceTraderEngine;
    }



    public void logInSetUp(Stage stage){

        BorderPane borderPaneNotLoggedIn = new BorderPane();
        Scene scene = new Scene(borderPaneNotLoggedIn, 1400, 800);
        scene.getStylesheets().add("Style.css");
        stage.setScene(scene);
        stage.setTitle("Space Traders");

        Button checkServerBTN = new Button("Check Server Status");
        Label serverStatusLBL = new Label();
        VBox statusBox = new VBox(10);
        statusBox.getChildren().addAll(checkServerBTN, serverStatusLBL);

        BorderPane.setMargin(statusBox, new Insets(10));
        borderPaneNotLoggedIn.setTop(statusBox);
        statusView(checkServerBTN, serverStatusLBL, statusBox);

        VBox logInBox = new VBox(20);
        logInBox.getStyleClass().add("vbox");

        borderPaneNotLoggedIn.setLeft(logInBox);
        BorderPane.setMargin(logInBox, new Insets(50, 0, 0, 450));
        logInBox.setAlignment(Pos.CENTER_LEFT);

        VBox registerBox = new VBox(20);
        registerBox.getStyleClass().add("vbox");
        borderPaneNotLoggedIn.setRight(registerBox);
        BorderPane.setMargin(registerBox, new Insets(0, 350, 0, 0));
        registerBox.setAlignment(Pos.CENTER_RIGHT);

        Label loginTitle = new Label("Login");
        loginTitle.setId("title");
        logInBox.getChildren().add(loginTitle);

        Label userNameLBL = new Label("Username: ");
        TextField userNameTF = new TextField();
        logInBox.getChildren().addAll(userNameLBL, userNameTF);

        Label tokenLBL = new Label("Token: ");
        TextField tokenTF = new TextField("");
        logInBox.getChildren().addAll(tokenLBL, tokenTF);

        Label registerTitle = new Label("Register");
        registerTitle.setId("title");
        registerBox.getChildren().add(registerTitle);

        Label newUserNameLBL = new Label("Username: ");
        TextField newUserNameTF = new TextField();
        registerBox.getChildren().addAll(newUserNameLBL, newUserNameTF);

        Button registerBTN = new Button("Register");
        registerBox.getChildren().add(registerBTN);

        TextArea registerTA= new TextArea();
        registerTA.setEditable(false);
        registerBox.getChildren().add(registerTA);

        registerBTN.setOnAction((ActionEvent e) -> {

            String result = spaceTraderEngine.addNewUser(newUserNameTF.getText());

            registerTA.setText(result);
        });

        Button signInBTN = new Button("Sign in");
        logInBox.getChildren().add(signInBTN);

        BorderPane borderPaneLoggedIn = new BorderPane();
        Scene loggedInScene = new Scene(borderPaneLoggedIn, 1400, 800);

        Label resultLBL = new Label();
        resultLBL.getStylesheets().add("Result.css");
        logInBox.getChildren().add(resultLBL);

        signInBTN.setOnAction((ActionEvent e) -> {

            String result = spaceTraderEngine.login(userNameTF.getText(), tokenTF.getText());

            if (result.equals("true")){
                stage.setScene(loggedInScene);
                loggedInSetUp(loggedInScene, stage);

            } else{
                resultLBL.setText(result);

            }
        });

        stage.show();
    }

    public void statusView(Button checkServerBTN, Label serverStatusLBL, VBox statusBox) {
        statusBox.setAlignment(Pos.TOP_RIGHT);
        checkServerBTN.setOnAction((ActionEvent e) -> {

            String result = spaceTraderEngine.isServerActive();
            serverStatusLBL.setText(result);

            if (result.startsWith("ERROR: ")){
                serverStatusLBL.setTextFill(Paint.valueOf("#DC4E33"));
            } else {
                serverStatusLBL.setTextFill(Paint.valueOf("#56BC4C"));
            }


            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> serverStatusLBL.setText(""));
            pause.play();

        });
    }


    public void loggedInSetUp(Scene loggedInScene, Stage stage){

        loggedInScene.getStylesheets().add("Style.css");

        BorderPane borderPaneLoggedIn = (BorderPane) loggedInScene.getRoot();
        welcomeView(loggedInScene);

        Label userInfoLBL = new Label(spaceTraderEngine.getUserInfo());
        userInfoLBL.setId("FP");
        userInfoLBL.setAlignment(Pos.BASELINE_LEFT);
        userInfoLBL.setPadding(new Insets(0, 0, 0, 10));
        userInfoLBL.setMaxWidth(300);

        Label titleLBL = new Label();
        titleLBL.setAlignment(Pos.TOP_CENTER);
        titleLBL.setId("title");


        GridPane top = new GridPane();
        top.add(userInfoLBL, 0, 0);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        top.add(titleLBL, 1, 0);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        GridPane.setHalignment(titleLBL, HPos.RIGHT);
        titleLBL.setPadding(new Insets(0, 0, 0, 160));


        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        top.getColumnConstraints().addAll(col1, col2, col3);
        top.setAlignment(Pos.TOP_LEFT);

        Button checkServerBTN = new Button("Check Server Status");
        Label serverStatusLBL = new Label();
        VBox statusBox = new VBox(10);
        GridPane.setMargin(statusBox, new Insets(10));
        statusBox.getChildren().addAll(checkServerBTN, serverStatusLBL);

        statusView(checkServerBTN, serverStatusLBL, statusBox);
        top.add(statusBox, 2, 0);



        borderPaneLoggedIn.setTop(top);


        FlowPane menuFP = new FlowPane(Orientation.VERTICAL);
        borderPaneLoggedIn.setLeft(menuFP);
        menuFP.setPadding(new Insets(5, 0, 5, 0));
        menuFP.setColumnHalignment(HPos.LEFT);


        Map<String, String> commands = new LinkedHashMap<>();
        commands.put("List Available Loans", "transfer.png");
        commands.put("Obtain a Loan", "transfer.png");
        commands.put("List Active Loans", "transfer.png");
        commands.put("List Available Ships", "spaceship.png" );
        commands.put("Purchase a Ship", "spaceship.png");
        commands.put("List My Ships", "spaceship.png");
        commands.put("Purchase Ship Fuel", "spaceship.png");
        commands.put("View Local Marketplace", "market.png");
        commands.put("Purchase Goods", "market.png");
        commands.put("Sell Goods", "market.png");
        commands.put("Nearby Locations", "pin.png");
        commands.put("Create a Flight Plan", "spaceship.png" );
        commands.put("View Current Flight Plans", "spaceship.png");


        for (String command: commands.keySet()){
            ImageView icon = new ImageView(commands.get(command));
            icon.setFitHeight(40.0);
            icon.setFitWidth(40.0);
            Button newButton = new Button(command); // put menu label
            newButton.setGraphic(icon);
            newButton.setId("FP");
            newButton.setOnAction((ActionEvent e) -> {
                switch (command) {
                    case "List Available Loans" -> {
                        titleLBL.setText("List Available Loans");
                        listAvailableLoansView(loggedInScene);
                    }
                    case "Obtain a Loan" -> {
                        titleLBL.setText("Obtain a Loan");
                        obtainALoanView(loggedInScene, userInfoLBL);
                    }
                    case "List Active Loans" -> {
                        titleLBL.setText("List Active Loans");
                        listActiveLoansView(loggedInScene);
                    }
                    case "List Available Ships" -> {
                        titleLBL.setText("List Available Ships");
                        listAvailableShipsView(loggedInScene);
                    }
                    case "Purchase a Ship" -> {
                        titleLBL.setText("Purchase a Ship");
                        purchaseAShipView(loggedInScene, userInfoLBL);
                    }
                    case "List My Ships" -> {
                        titleLBL.setText("List My Ships");
                        listMyShipsView(loggedInScene);
                    }
                    case "Purchase Ship Fuel" -> {
                        titleLBL.setText("Purchase Ship Fuel");
                        purchaseShipFuelView(loggedInScene, userInfoLBL);
                    }
                    case "View Local Marketplace" -> {
                        titleLBL.setText("View Local Marketplace");
                        localMarketPlaceView(loggedInScene);
                    }
                    case "Purchase Goods" -> {
                        titleLBL.setText("Purchase Goods");
                        purchaseGoodView(loggedInScene, userInfoLBL);
                    }
                    case "Sell Goods" -> {
                        titleLBL.setText("Sell Goods");
                        sellMyGoodView(loggedInScene, userInfoLBL);
                    }
                    case "Nearby Locations" -> {
                        titleLBL.setText("Nearby Locations");
                        listNearbyLocationsView(loggedInScene);
                    }
                    case "Create a Flight Plan" -> {
                        titleLBL.setText("Create a Flight Plan");
                        createNewFlightPlanView(loggedInScene, userInfoLBL);
                    }
                    case "View Current Flight Plans" -> {
                        titleLBL.setText("View Current Flight Plans");
                        currentFlightPlanView(loggedInScene);
                    }
                }
            });
            menuFP.getChildren().add(newButton);
        }

    }

    public void welcomeView(Scene scene){
        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        Label welcomeLBL = new Label("Welcome");
        welcomeLBL.setId("title");
        borderPaneLoggedIn.setCenter(welcomeLBL);

    }


    public void listAvailableLoansView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listAvailableLoans();

        if (out.equals("ok")){

            TableView loansTable = new TableView();

            setUpLoansTable(loansTable);

            borderPaneLoggedIn.setCenter(loansTable);

            List<Loan> loans = spaceTraderEngine.getAvailableLoans();

            for (Loan loan: loans){
                loansTable.getItems().add(loan);
            }



        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }

    public void obtainALoanView(Scene scene, Label userInfoLBL){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listAvailableLoans();

        if (out.equals("ok")){

            VBox loanBox = new VBox(20);
            Label loanLBL = new Label("Select a loan from the table");
            loanBox.setAlignment(Pos.TOP_CENTER);

            Button confirm = new Button("Confirm");

            TableView loansTable = new TableView();
            TableView.TableViewSelectionModel selectionModel = loansTable.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            loanBox.getChildren().addAll(loanLBL, loansTable);


            Label resultLbl = new Label();
            resultLbl.getStylesheets().add("Result.css");

            loanBox.getChildren().addAll(confirm, resultLbl);
            confirm.setOnAction((ActionEvent e) -> {

                if (selectionModel.getSelectedItem() != null){

                    Loan selectedLoan = (Loan) selectionModel.getSelectedItem();
                    String result = spaceTraderEngine.obtainLoan(selectedLoan.getType());

                    if (result.startsWith("ERROR")){
                        resultLbl.setId("failure");
                    } else {
                        resultLbl.setId("success");
                    }

                    resultLbl.setText(result);
                    // update user info
                    userInfoLBL.setText(spaceTraderEngine.getUserInfo());
                } else {
                    resultLbl.setId("failure");
                    resultLbl.setText("Please select loan before confirming purchase");
                }
            });

            setUpLoansTable(loansTable);

            borderPaneLoggedIn.setCenter(loanBox);

            List<Loan> loans = spaceTraderEngine.getAvailableLoans();

            if (loans != null){
                for (Loan loan: loans){
                    loansTable.getItems().add(loan);

                }
            }



        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }

    public void setUpLoansTable(TableView loansTable){

        loansTable.setPlaceholder(new Label("No loans available"));

        loansTable.setPadding(new Insets (20));
        loansTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Loan, Integer> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loansTable.getColumns().add(amountCol);

        TableColumn<Loan, Boolean> collateralCol = new TableColumn<>("Collateral Required");
        collateralCol.setCellValueFactory(new PropertyValueFactory<>("collateralRequired"));
        loansTable.getColumns().add(collateralCol);

        TableColumn<Loan, Integer> rateCol = new TableColumn<>("Rate");
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        loansTable.getColumns().add(rateCol);

        TableColumn<Loan, Integer> termCol = new TableColumn<>("Term In Days");
        termCol.setCellValueFactory(new PropertyValueFactory<>("termInDays"));
        loansTable.getColumns().add(termCol);

        TableColumn<Loan, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        loansTable.getColumns().add(typeCol);
    }





    public void listActiveLoansView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listActiveLoans();

        if (out.equals("ok")){

            TableView loansTable = new TableView();
            loansTable.setPlaceholder(new Label("You have no active loans"));
            loansTable.setPadding(new Insets (20));
            loansTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            borderPaneLoggedIn.setCenter(loansTable);

            TableColumn<ActiveLoan, Integer> dueCol = new TableColumn<>("Due");
            dueCol.setCellValueFactory(new PropertyValueFactory<>("due"));
            loansTable.getColumns().add(dueCol);

            TableColumn<ActiveLoan, Boolean> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            loansTable.getColumns().add(idCol);

            TableColumn<ActiveLoan, Integer> repayCol = new TableColumn<>("Repayment Amount");
            repayCol.setCellValueFactory(new PropertyValueFactory<>("repaymentAmount"));
            loansTable.getColumns().add(repayCol);

            TableColumn<ActiveLoan, Integer> statusCol = new TableColumn<>("Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            loansTable.getColumns().add(statusCol);

            TableColumn<ActiveLoan, Integer> typeCol = new TableColumn<>("Type");
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            loansTable.getColumns().add(typeCol);

            borderPaneLoggedIn.setCenter(loansTable);

            List<ActiveLoan> loans = spaceTraderEngine.getActiveLoans();

            for (ActiveLoan loan: loans){
                loansTable.getItems().add(loan);
            }



        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }

    public void listAvailableShipsView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listAvailableShips();

        if (out.equals("ok")){

            TableView shipsTable = new TableView();


            borderPaneLoggedIn.setCenter(shipsTable);
            setUpAvailableShipsTable(shipsTable);

            List<Ship> ships = spaceTraderEngine.getListAvailableShips();

            for (Ship ship: ships){
                shipsTable.getItems().add(ship);

            }



        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }


    public void setUpAvailableShipsTable(TableView shipsTable){

        shipsTable.setPlaceholder(new Label("No ships available"));

        shipsTable.getStylesheets().add("Ships.css");
        shipsTable.setPadding(new Insets (20));
        shipsTable.setId("ship");
        shipsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Ship, String> col1 = new TableColumn<>("Class");
        col1.setCellValueFactory(new PropertyValueFactory<>("shipClass"));
        shipsTable.getColumns().add(col1);

        TableColumn<Ship, String> col2 = new TableColumn<>("Manufacturer");
        col2.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        shipsTable.getColumns().add(col2);

        TableColumn<Ship, Integer> col3 = new TableColumn<>("Max Cargo");
        col3.setCellValueFactory(new PropertyValueFactory<>("maxCargo"));
        shipsTable.getColumns().add(col3);

        TableColumn<Ship, Integer> col4 = new TableColumn<>("Plating");
        col4.setCellValueFactory(new PropertyValueFactory<>("plating"));
        shipsTable.getColumns().add(col4);

        TableColumn<Ship, PurchaseLocation> col5 = new TableColumn<>("Purchase Location");
        TableColumn<Ship, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> s) {
                Ship ship = s.getValue();
                StringBuilder out = new StringBuilder();
                for (PurchaseLocation purchaseLocation: ship.getPurchaseLocations()){
                    out.append(purchaseLocation.getLocation());
                    out.append("\n");
                }
                return new SimpleStringProperty(out.toString());
            }
        });

        TableColumn<Ship, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> s) {
                Ship ship = s.getValue();
                StringBuilder out = new StringBuilder();
                for (PurchaseLocation purchaseLocation: ship.getPurchaseLocations()){
                    out.append(purchaseLocation.getPrice());
                    out.append("\n");
                }
                return new SimpleStringProperty(out.toString());
            }
        });

        TableColumn<Ship, String> systemCol = new TableColumn<>("System");
        systemCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> s) {
                Ship ship = s.getValue();
                StringBuilder out = new StringBuilder();
                for (PurchaseLocation purchaseLocation: ship.getPurchaseLocations()){
                    out.append(purchaseLocation.getSystem());
                    out.append("\n");
                }
                return new SimpleStringProperty(out.toString());
            }
        });

        col5.getColumns().addAll(locationCol, priceCol, systemCol);
        shipsTable.getColumns().add(col5);

        TableColumn<Ship, Integer> col6 = new TableColumn<>("Speed");
        col6.setCellValueFactory(new PropertyValueFactory<>("speed"));
        shipsTable.getColumns().add(col6);

        TableColumn<Ship, Integer> col7 = new TableColumn<>("Loading\nSpeed");
        col7.setCellValueFactory(new PropertyValueFactory<>("loadingSpeed"));
        shipsTable.getColumns().add(col7);



        TableColumn<Ship, String> col8 = new TableColumn<>("Type");
        col8.setCellValueFactory(new PropertyValueFactory<>("type"));
        shipsTable.getColumns().add(col8);

        TableColumn<Ship, Integer> col9 = new TableColumn<>("Weapons");
        col9.setCellValueFactory(new PropertyValueFactory<>("weapons"));
        shipsTable.getColumns().add(col9);

        TableColumn<Ship, String> col10 = new TableColumn<>("Restricted\nGoods");
        col10.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> s) {
                Ship ship = s.getValue();

                StringBuilder out = new StringBuilder();
                if (ship.getRestrictedGoods() == null || ship.getRestrictedGoods().size() == 0){
                    out = new StringBuilder("No\nrestricted\ngoods");
                } else {
                    for (String restrictedGood: ship.getRestrictedGoods()){
                        out.append(restrictedGood);
                        out.append("\n");
                    }

                }
                return new SimpleStringProperty(out.toString());
            }
        });
        shipsTable.getColumns().add(col10);



    }


    public void purchaseAShipView(Scene scene, Label userInfoLBL){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listAvailableShips();

        if (out.equals("ok")){

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.fitToWidthProperty().set(true);
            borderPaneLoggedIn.setCenter(scrollPane);

            VBox shipBox = new VBox(20);
            Label shipLBL = new Label("Select a ship from the table");
            shipBox.setAlignment(Pos.TOP_CENTER);
            shipBox.setPadding(new Insets(0, 0, 50, 0));

            scrollPane.setContent(shipBox);

            Button confirm = new Button("Confirm");

            TableView shipsTable = new TableView();

            TableView.TableViewSelectionModel selectionModel = shipsTable.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            shipBox.getChildren().addAll(shipLBL, shipsTable);

            ComboBox<String> locationPicker = new ComboBox<>();

            shipLBL.getStylesheets().add("Result.css");
            Label resultLbl = new Label();

            resultLbl.getStylesheets().add("Result.css");


            shipBox.getChildren().addAll(locationPicker, confirm, resultLbl);
            confirm.setOnAction((ActionEvent e) -> {

                if (selectionModel.getSelectedItem() != null){

                    Ship selectedShip = (Ship) selectionModel.getSelectedItem();

                    if (locationPicker.getValue() == null){
                        resultLbl.setId("failure");
                        resultLbl.setText("Please select location before confirming purchase");
                        return;
                    }

                    String result = spaceTraderEngine.purchaseShip(locationPicker.getValue(), selectedShip.getType());

                    if (result.startsWith("ERROR")){
                        resultLbl.setId("failure");
                    } else {
                        // update user info
                        userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                        resultLbl.setId("success");
                    }
                    resultLbl.setText(result);

                    // update user info
                    userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                } else {
                    resultLbl.setId("failure");
                    resultLbl.setText("Please select ship before confirming purchase");
                }
            });

            setUpAvailableShipsTable(shipsTable);

            List<Ship> ships = spaceTraderEngine.getListAvailableShips();

            for (Ship ship: ships){
                shipsTable.getItems().add(ship);
            }


            selectionModel.selectedItemProperty().addListener( (observable) -> {
                shipLBL.setText("Please select a location");
                if (selectionModel.getSelectedItem() != null){
                    Ship selectedShip = (Ship) selectionModel.getSelectedItem();
                    locationPicker.getItems().clear();
                    for (PurchaseLocation location: selectedShip.getPurchaseLocations()){
                        locationPicker.getItems().add(location.getLocation());
                    }
                }
            });



        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }


    public void listMyShipsView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listMyShips();


        if (out.equals("ok")){

            TableView shipsTable = new TableView();
            borderPaneLoggedIn.setCenter(shipsTable);
            shipsTable.setPadding(new Insets (20));
            shipsTable.setId("ship");
            setUpMyShipsTable(shipsTable);

            List<Ship> ships = spaceTraderEngine.getMyShips();

            for (Ship ship: ships){
                shipsTable.getItems().add(ship);

            }


        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }

    public void purchaseShipFuelView(Scene scene, Label userInfoLBL){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listMyShips();

        if (out.equals("ok")){

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.fitToWidthProperty().set(true);
            borderPaneLoggedIn.setCenter(scrollPane);

            Label shipLBL = new Label("Select a ship from the table");
            VBox shipBox = new VBox(20);
            shipBox.setAlignment(Pos.TOP_CENTER);
            scrollPane.setContent(shipBox);
            shipBox.setPadding(new Insets(0, 0, 50, 0));

            Button confirm = new Button("Confirm");

            TableView shipsTable = new TableView();
            shipsTable.getStylesheets().add("Ships.css");
            TableView.TableViewSelectionModel selectionModel = shipsTable.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            shipBox.getChildren().addAll(shipLBL, shipsTable);


            Label fuelLBL = new Label("Type a quantity of fuel below\n(integer only)");
            fuelLBL.getStylesheets().add("Result.css");
            TextField quantityTF = new TextField("");
            Label resultLbl = new Label();
            resultLbl.getStylesheets().add("Result.css");

            shipBox.getChildren().addAll(fuelLBL, quantityTF, confirm, resultLbl);
            confirm.setOnAction((ActionEvent e) -> {

                if (selectionModel.getSelectedItem() != null){

                    Ship selectedShip = (Ship) selectionModel.getSelectedItem();

                    try {
                        Integer.parseInt(quantityTF.getText());
                    } catch (NumberFormatException nfe){
                        resultLbl.setId("failure");
                        resultLbl.setText("ERROR: Please enter an integer");
                        return;
                    }

                    String result = spaceTraderEngine.purchaseShipFuel(selectedShip.getId(), Integer.parseInt(quantityTF.getText()));

                    if (result.startsWith("ERROR")){
                        resultLbl.setId("failure");
                    } else {

                        refreshMyShips(shipsTable, selectionModel);

                        // update user info
                        userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                        resultLbl.setId("success");
                    }
                    resultLbl.setText(result);



                } else {
                    resultLbl.setId("failure");
                    resultLbl.setText("ERROR: Please select ship before confirming purchase");
                }
            });

            setUpMyShipsTable(shipsTable);

            List<Ship> ships = spaceTraderEngine.getMyShips();

            for (Ship ship: ships){
                shipsTable.getItems().add(ship);

            }

        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }

    private void refreshMyShips(TableView shipsTable, TableView.TableViewSelectionModel selectionModel) {
        int selected = selectionModel.getSelectedIndex();

        spaceTraderEngine.listMyShips();

        shipsTable.getItems().clear();

        List<Ship> ships = spaceTraderEngine.getMyShips();

        for (Ship ship: ships){
            shipsTable.getItems().add(ship);
        }

        selectionModel.select(selected);
    }


    public void localMarketPlaceView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        VBox marketBox = new VBox(20);
        marketBox.setAlignment(Pos.TOP_CENTER);
        borderPaneLoggedIn.setCenter(marketBox);

        Label selectLBL = new Label("Select a location");
        marketBox.getChildren().add(selectLBL);

        ComboBox<String> selectLocation = new ComboBox();

        String checkLocations = spaceTraderEngine.getNearByLocations();

        if (!checkLocations.startsWith("ERROR: ")){

            for (Location location: spaceTraderEngine.getNearbyLocationsList()){
                selectLocation.getItems().add(location.getSymbol());
            }

            marketBox.getChildren().addAll(selectLocation);

        } else {
            Label errorMsg = new Label(checkLocations);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
            return;
        }

        TableView marketTable = new TableView();
        marketBox.getChildren().add(marketTable);
        marketTable.getStylesheets().add("Market.css");
        marketTable.setPlaceholder(new Label("Please select a location first"));
        marketTable.setPadding(new Insets (20, 20, 0, 20));
        marketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setUpMarketView(marketTable);

        Label errorMsg = new Label();
        marketBox.getChildren().add(errorMsg);


        selectLocation.getSelectionModel().selectedItemProperty().addListener((selected) -> {
            String out = spaceTraderEngine.viewLocalMarketPlace(selectLocation.getValue());

            if (out.equals("ok")){
                errorMsg.setText("");
                errorMsg.setId("gone");

                marketTable.getItems().clear();


                List<Good> goods = spaceTraderEngine.getMarketPlace();

                for (Good good: goods){
                    marketTable.getItems().add(good);
                }

            } else {
                marketTable.getItems().clear();
                errorMsg.setId("failure");
                errorMsg.setText(out);

            }

        });


    }

    private void setUpMarketView(TableView marketTable) {
        TableColumn<Good, String> col1 = new TableColumn<>("Symbol");
        col1.setCellValueFactory(new PropertyValueFactory<>("formatted"));
        marketTable.getColumns().add(col1);


        TableColumn<Good, Integer> col3 = new TableColumn<>("Price Per Unit");
        col3.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        marketTable.getColumns().add(col3);

        TableColumn<Good, Integer> col4 = new TableColumn<>("Purchase Price Per Unit");
        col4.setCellValueFactory(new PropertyValueFactory<>("purchasePricePerUnit"));
        marketTable.getColumns().add(col4);


        TableColumn<Good, Integer> col5 = new TableColumn<>("Quantity Available");
        col5.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));
        marketTable.getColumns().add(col5);

        TableColumn<Good, Integer> col6 = new TableColumn<>("Sell Price Per Unit");
        col6.setCellValueFactory(new PropertyValueFactory<>("sellPricePerUnit"));
        marketTable.getColumns().add(col6);

        TableColumn<Good, Integer> col7= new TableColumn<>("Spread");
        col7.setCellValueFactory(new PropertyValueFactory<>("spread"));
        marketTable.getColumns().add(col7);

        TableColumn<Good, Integer> col8 = new TableColumn<>("Volume Per Unit");
        col8.setCellValueFactory(new PropertyValueFactory<>("volumePerUnit"));
        marketTable.getColumns().add(col8);
    }


    public void setUpMyShipsTable(TableView shipsTable){

        shipsTable.getStylesheets().add("Ships.css");
        shipsTable.setPadding(new Insets (0, 20, 0, 20));
        shipsTable.setId("ship");
        shipsTable.setPlaceholder(new Label("You have no ships"));

        TableColumn<Ship, String> col1 = new TableColumn<>("Ship ID\nFlight Plan ID");
        col1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> s) {
                Ship ship = s.getValue();
                String out = ship.getId() + "\n";
                if (ship.getFlightPlanId() == null){
                    out += "Not in transit";
                } else {
                    out += ship.getFlightPlanId();
                }

                return new SimpleStringProperty(out);
            }
        });
        shipsTable.getColumns().add(col1);

        TableColumn<Ship, String> col5 = new TableColumn<>("Class");
        col5.setCellValueFactory(new PropertyValueFactory<>("shipClass"));
        shipsTable.getColumns().add(col5);

        TableColumn<Ship, String> col2 = new TableColumn<>("Type");
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        shipsTable.getColumns().add(col2);


        TableColumn<Ship, String> col3 = new TableColumn<>("Cargo");
        col3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> s) {
                Ship ship = s.getValue();
                StringBuilder out = new StringBuilder();
                if (ship.getCargo().size() == 0){
                    out = new StringBuilder("No cargo");
                } else {
                    for (Cargo cargo: ship.getCargo()){
                        out.append(cargo.toString());
                        out.append("\n");
                    }
                }
                return new SimpleStringProperty(out.toString());
            }
        });
        shipsTable.getColumns().add(col3);

        TableColumn<Ship, String> col9 = new TableColumn<>("Space\nAvailable");
        col9.setCellValueFactory(new PropertyValueFactory<>("spaceAvailable"));
        shipsTable.getColumns().add(col9);

        TableColumn<Ship, Integer> col7 = new TableColumn<>("Max\nCargo");
        col7.setCellValueFactory(new PropertyValueFactory<>("maxCargo"));
        shipsTable.getColumns().add(col7);

        TableColumn<Ship, String> col4 = new TableColumn<>("Location");
        col4.setCellValueFactory(new PropertyValueFactory<>("location"));
        shipsTable.getColumns().add(col4);


        TableColumn<Ship, PurchaseLocation> col6 = new TableColumn<>("Manufacturer");
        col6.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        shipsTable.getColumns().add(col6);



        TableColumn<Ship, String> col8 = new TableColumn<>("Plating");
        col8.setCellValueFactory(new PropertyValueFactory<>("plating"));
        shipsTable.getColumns().add(col8);


        TableColumn<Ship, Integer> col10 = new TableColumn<>("Speed");
        col10.setCellValueFactory(new PropertyValueFactory<>("speed"));
        shipsTable.getColumns().add(col10);

        TableColumn<Ship, Integer> col11 = new TableColumn<>("Loading\nSpeed");
        col11.setCellValueFactory(new PropertyValueFactory<>("loadingSpeed"));
        shipsTable.getColumns().add(col11);



        TableColumn<Ship, Integer> col13 = new TableColumn<>("Weapons");
        col13.setCellValueFactory(new PropertyValueFactory<>("weapons"));
        shipsTable.getColumns().add(col13);

        TableColumn<Ship, Integer> col14 = new TableColumn<>("x");
        col14.setCellValueFactory(new PropertyValueFactory<>("x"));
        shipsTable.getColumns().add(col14);

        TableColumn<Ship, Integer> col15 = new TableColumn<>("y");
        col15.setCellValueFactory(new PropertyValueFactory<>("y"));
        shipsTable.getColumns().add(col15);
    }


    public void purchaseGoodView(Scene scene, Label userInfoLBL){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        borderPaneLoggedIn.setCenter(scrollPane);

        VBox marketBox = new VBox(20);
        marketBox.setPadding(new Insets(0, 0, 30, 0));
        marketBox.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(marketBox);

        HBox selectBox = new HBox(20);
        marketBox.getChildren().add(selectBox);
        selectBox.setPadding(new Insets(10, 0, 10, 200));

        Label selectLocationLBL = new Label("Select a location");
        ComboBox<String> selectLocation = new ComboBox();
        selectBox.getChildren().add(selectLocationLBL);

        Label resultLbl = new Label();
        resultLbl.getStylesheets().add("Result.css");

        String checkLocations = spaceTraderEngine.getNearByLocations();

        if (!checkLocations.startsWith("ERROR: ")){

            for (Location location: spaceTraderEngine.getNearbyLocationsList()){
                selectLocation.getItems().add(location.getSymbol());
            }

            selectBox.getChildren().addAll(selectLocation);

        } else {
            resultLbl.setText(checkLocations);
            resultLbl.setId("failure");
            borderPaneLoggedIn.setCenter(resultLbl);
            return;
        }


        Label selectShipLBL = new Label("Select a ship");
        ComboBox<String> selectShip = new ComboBox();
        selectBox.getChildren().add(selectShipLBL);

        String checkShips = spaceTraderEngine.listMyShips();

        if (checkShips.startsWith("ERROR: ") == false && !checkShips.equals("You have no ships")){

            for (Ship ship: spaceTraderEngine.getMyShips()){
                selectShip.getItems().add(ship.getId());
            }

            selectBox.getChildren().addAll(selectShip);

        } else {
            resultLbl.setText(checkShips);
            resultLbl.setId("failure");
            borderPaneLoggedIn.setCenter(resultLbl);
            return;
        }

        TableView marketTable = new TableView();
        Label goodLbl = new Label("Please select a good from the table");
        marketBox.getChildren().addAll(goodLbl, marketTable);
        marketTable.getStylesheets().add("Market.css");
        marketTable.setPlaceholder(new Label("Please select a location first"));
        TableView.TableViewSelectionModel selectionModel = marketTable.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        marketTable.setPadding(new Insets (10, 20, 0, 20));
        marketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setUpMarketView(marketTable);


        Label shipLBL = new Label("Type a quantity below\n(integer only)");
        shipLBL.getStylesheets().add("Result.css");
        TextField quantityTF = new TextField("");
        marketBox.getChildren().addAll(shipLBL, quantityTF);

        Button confirm = new Button("Confirm");


        TableView updatedShip = new TableView();
        setUpMyShipsTable(updatedShip);

        marketBox.getChildren().addAll(confirm , resultLbl);
        confirm.setOnAction((ActionEvent e) -> {

            if (selectionModel.getSelectedItem() != null){

                Good good  = (Good) selectionModel.getSelectedItem();

                if (selectShip.getValue() == null ){
                    resultLbl.setId("failure");
                    resultLbl.setText("ERROR: Please select a ship");
                    return;
                }
                resultLbl.setId("failure");
                resultLbl.setText("Please select a good before confirming purchase");

                try {
                    Integer.parseInt(quantityTF.getText());

                } catch (NumberFormatException nfe){
                    resultLbl.setId("failure");
                    resultLbl.setText("ERROR: Please enter an integer");
                    return;
                }

                String result = spaceTraderEngine.purchaseGood(selectShip.getValue(), good.getSymbol(), Integer.parseInt(quantityTF.getText()));

                if (result.startsWith("ERROR")){
                    resultLbl.setId("failure");
                } else {

                    if (!marketBox.getChildren().contains(updatedShip)){
                        marketBox.getChildren().addAll(updatedShip);
                    }

                    updatedShip.getItems().clear();

                    updatedShip.getItems().add(spaceTraderEngine.getUpdatedShip());

                    // update user info
                    userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                    resultLbl.setId("success");
                }

                resultLbl.setText(result);
                // update user info
                userInfoLBL.setText(spaceTraderEngine.getUserInfo());

            } else {
                resultLbl.setId("failure");
                resultLbl.setText("Please select a good before confirming purchase");
            }
        });


        selectLocation.getSelectionModel().selectedItemProperty().addListener((selected) -> {
            String out = spaceTraderEngine.viewLocalMarketPlace((String) selectLocation.getValue());

            if (out.equals("ok")){
                resultLbl.setText("");
                resultLbl.setId("gone");

                marketTable.getItems().clear();


                List<Good> goods = spaceTraderEngine.getMarketPlace();

                for (Good good: goods){
                    marketTable.getItems().add(good);
                }

            } else {
                marketTable.getItems().clear();
                resultLbl.setId("failure");
                resultLbl.setText(out);

            }

        });


    }

    public void sellMyGoodView(Scene scene, Label userInfoLBL){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        borderPaneLoggedIn.setCenter(scrollPane);

        VBox saleBox = new VBox(20);
        saleBox.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(saleBox);
        saleBox.setPadding(new Insets(0, 0, 50, 0));

        String out = spaceTraderEngine.listMyShips();

        Label errorMsg = new Label();

        if (out.equals("ok")){

            Label shipLBL = new Label("Select a ship from the table");

            TableView shipsTable = new TableView();
            saleBox.getChildren().addAll(shipLBL, shipsTable);
            SelectionModel selectionModel = shipsTable.getSelectionModel();
            shipsTable.setId("ship");
            setUpMyShipsTable(shipsTable);

            List<Ship> ships = spaceTraderEngine.getMyShips();

            for (Ship ship: ships){
                shipsTable.getItems().add(ship);
            }

            HBox selectBox = new HBox(20);
            saleBox.getChildren().add(selectBox);
            selectBox.setPadding(new Insets(0, 0, 0, 200));

            Label selectGoodLBL = new Label("Select a good to sell");
            ComboBox<String> selectGood = new ComboBox();
            selectBox.getChildren().addAll(selectGoodLBL, selectGood);

            Label quantityLBL = new Label("Type a quantity\n(integer only)");
            quantityLBL.getStylesheets().add("Result.css");
            TextField quantityTF = new TextField("");
            selectBox.getChildren().addAll(quantityLBL, quantityTF);

            Button confirm = new Button("Confirm");
            confirm.setAlignment(Pos.TOP_CENTER);

            Label resultLbl = new Label();
            resultLbl.getStylesheets().add("Result.css");

            saleBox.getChildren().addAll(confirm, resultLbl);


            selectionModel.selectedItemProperty().addListener((selected) -> {

                if (selectionModel.getSelectedItem() != null){

                    selectGood.getItems().clear();

                    Ship ship = (Ship) selectionModel.getSelectedItem();

                    for (Cargo cargo : ship.getCargo()){
                        selectGood.getItems().add(cargo.getGood());

                    }


                }


            });


            confirm.setOnAction((ActionEvent e) -> {

                if (selectionModel.getSelectedItem() != null){

                    Ship ship = (Ship) selectionModel.getSelectedItem();

                    if (selectGood.getValue() == null){
                        resultLbl.setId("failure");
                        resultLbl.setText("ERROR: Please select a good to sell");
                        return;
                    }

                    try {
                        Integer.parseInt(quantityTF.getText());
                    } catch (NumberFormatException nfe){
                        resultLbl.setId("failure");
                        resultLbl.setText("ERROR: Please enter an integer");
                        return;
                    }

                    String result = spaceTraderEngine.sellGood(ship.getId(), selectGood.getValue(), Integer.parseInt(quantityTF.getText()));

                    if (result.startsWith("ERROR")){
                        resultLbl.setId("failure");
                    } else {

                        int selected = selectionModel.getSelectedIndex();

                        shipsTable.getItems().clear();

                        spaceTraderEngine.listMyShips();

                        List<Ship> shipsList = spaceTraderEngine.getMyShips();
//
                        for (Ship ship1: shipsList){
                            shipsTable.getItems().add(ship1);
                        }

                        selectionModel.select(selected);

                        // update user info
                        userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                        resultLbl.setId("success");
                    }

                    resultLbl.setText(result);
                    // update user info
                    userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                } else {
                    resultLbl.setId("failure");
                    resultLbl.setText("ERROR: Please select a ship before confirming sale");
                }
            });



        } else {
            errorMsg.setText(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }

    public void listNearbyLocationsView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.getNearByLocations();

        if (out.equals("ok")){

            TableView locationsTable = new TableView();
            locationsTable.getStylesheets().add("Location.css");
            borderPaneLoggedIn.setCenter(locationsTable);

            locationsTable.setPadding(new Insets (20));
            locationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


            TableColumn<Location, String> col1 = new TableColumn<>("Name");
            col1.setCellValueFactory(new PropertyValueFactory<>("name"));
            locationsTable.getColumns().add(col1);
            col1.setMaxWidth(2500);

            TableColumn<Location, String> col2 = new TableColumn<>("Symbol");
            col2.setCellValueFactory(new PropertyValueFactory<>("symbol"));
            locationsTable.getColumns().add(col2);
            col2.setMaxWidth(1500);

            TableColumn<Location, String> col3 = new TableColumn<>("Type");
            col3.setCellValueFactory(new PropertyValueFactory<>("type"));
            locationsTable.getColumns().add(col3);
            col3.setMaxWidth(2000);

            TableColumn<Location, String> col4 = new TableColumn<>("Allows\nConstruction");
            col4.setCellValueFactory(new PropertyValueFactory<>("allowsConstruction"));
            locationsTable.getColumns().add(col4);
            col4.setMaxWidth(2000);

            TableColumn<Location, Integer> col5 = new TableColumn<>("x");
            col5.setCellValueFactory(new PropertyValueFactory<>("x"));
            locationsTable.getColumns().add(col5);
            col5.setMaxWidth(1000);

            TableColumn<Location, Integer> col6 = new TableColumn<>("y");
            col6.setCellValueFactory(new PropertyValueFactory<>("y"));
            locationsTable.getColumns().add(col6);
            col6.setMaxWidth(1000);

            TableColumn<Location, String> col7 = new TableColumn<>("Traits");
            col7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Location, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Location, String> l) {
                    Location location = l.getValue();

                    StringBuilder out = new StringBuilder();
                    if (location.getTraits() != null){
                        for (String trait: location.getTraits()){
                            out.append(trait);
                            out.append("\n");
                        }
                    }

                    return new SimpleStringProperty(out.toString());
                }
            });
            locationsTable.getColumns().add(col7);
            col7.setMaxWidth(6000);

            TableColumn<Location, String> col8 = new TableColumn<>("Messages");
            col8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Location, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Location, String> l) {
                    Location location = l.getValue();

                    StringBuilder out = new StringBuilder();
                    if (location.getMessages() != null){
                        for (String msg: location.getMessages()){
                            out.append(msg);
                            out.append("\n\n");
                        }
                    }

                    return new SimpleStringProperty(out.toString());
                }
            });
            locationsTable.getColumns().add(col8);


            List<Location> locations = spaceTraderEngine.getNearbyLocationsList();


            for (Location location: locations){
                locationsTable.getItems().add(location);

            }

        } else {
            Label errorMsg = new Label(out);
            errorMsg.setId("failure");
            borderPaneLoggedIn.setCenter(errorMsg);
        }

    }


    public void createNewFlightPlanView(Scene scene, Label userInfoLBL){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listMyShips();

        if (out.equals("ok")){

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.fitToWidthProperty().set(true);
            borderPaneLoggedIn.setCenter(scrollPane);

            VBox shipBox = new VBox(20);
            shipBox.setAlignment(Pos.TOP_CENTER);
            scrollPane.setContent(shipBox);
            shipBox.setPadding(new Insets(0, 0, 50, 0));

            Label shipLBL = new Label("Select a ship for your flight from below");
            shipLBL.getStylesheets().add("Result.css");
            shipBox.getChildren().add(shipLBL);

            TableView shipsTable = new TableView();
            shipsTable.getStylesheets().add("Ships.css");
            TableView.TableViewSelectionModel selectionModel = shipsTable.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            Button refreshTable = new Button("Refresh Flight Table");
            refreshTable.setOnAction((ActionEvent e) -> {
                refreshMyShips(shipsTable, selectionModel);
            });
            shipBox.getChildren().addAll(refreshTable, shipsTable);


            Label destinationLBL = new Label("Select a destination");
            destinationLBL.getStylesheets().add("Result.css");
            ComboBox<String> selectDestination = new ComboBox();

            String checkLocations = spaceTraderEngine.getNearByLocations();

            if (!checkLocations.startsWith("ERROR: ")){

                for (Location location: spaceTraderEngine.getNearbyLocationsList()){
                    selectDestination.getItems().add(location.getSymbol());
                }


            } else {
                destinationLBL.setText(checkLocations);
                destinationLBL.setId("failure");
                return;
            }

            Button confirm = new Button("Confirm");


            Label resultLbl = new Label();
            resultLbl.getStylesheets().add("Result.css");

            shipBox.getChildren().addAll(destinationLBL, selectDestination, confirm, resultLbl);
            confirm.setOnAction((ActionEvent e) -> {

                if (selectionModel.getSelectedItem() != null){

                    Ship selectedShip = (Ship) selectionModel.getSelectedItem();

                    if (selectDestination.getValue() == null){
                        resultLbl.setText("ERROR: Please select a destination before confirming");
                        resultLbl.setId("failure");
                        return;
                    }

                    String result = spaceTraderEngine.createFlightPlan(selectedShip.getId(),selectDestination.getValue() );

                    if (result.startsWith("ERROR")){
                        resultLbl.setId("failure");
                    } else {

//                        int selected = selectionModel.getSelectedIndex();

                        spaceTraderEngine.listMyShips();

                        shipsTable.getItems().clear();

                        List<Ship> ships = spaceTraderEngine.getMyShips();

                        int i = 0;
                        int selected = i;

                        for (Ship ship: ships){
                            shipsTable.getItems().add(ship);
                            if (ship.getId().equals(selectedShip.getId())){
                                selected = i;
                            }
                            i += 1;
                        }

                        selectionModel.select(selected);

                        // update user info
                        userInfoLBL.setText(spaceTraderEngine.getUserInfo());

                        resultLbl.setId("success");
                    }
                    resultLbl.setText(result);



                } else {
                    resultLbl.setId("failure");
                    resultLbl.setText("ERROR: Please select ship before confirming flight plan");
                }
            });

            setUpMyShipsTable(shipsTable);



            List<Ship> ships = spaceTraderEngine.getMyShips();

            for (Ship ship: ships){
                shipsTable.getItems().add(ship);

            }

        } else {
            Label errorMsg = new Label(out);
            borderPaneLoggedIn.setCenter(errorMsg);
            errorMsg.setId("failure");
        }

    }

    public void setUpMyFlightsTable(TableView flightsTable){

        flightsTable.getStylesheets().add("Ships.css");
        flightsTable.setPadding(new Insets (20));
        flightsTable.setId("ship");
        flightsTable.setPlaceholder(new Label("You have no flight plans"));

        TableColumn<FlightPlan, String> col1 = new TableColumn<>("Ship ID\nFlight Plan ID");
        col1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightPlan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightPlan, String> f) {
                FlightPlan flightPlan = f.getValue();
                String out = flightPlan.getShipId() + "\n";
                if (flightPlan.getId() == null){
                    out += "Not in transit";
                } else {
                    out += flightPlan.getId();
                }

                return new SimpleStringProperty(out);
            }
        });
        flightsTable.getColumns().add(col1);
        col1.setPrefWidth(250);

        TableColumn<FlightPlan, String> col3 = new TableColumn<>("Created at\nArrived at");
        col3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightPlan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightPlan, String> f) {
                FlightPlan flightPlan = f.getValue();
                String out = flightPlan.getCreatedAt() +"\n" + flightPlan.getArrivesAt();

                return new SimpleStringProperty(out);
            }
        });
        flightsTable.getColumns().add(col3);
        col3.setPrefWidth(200);

        TableColumn<FlightPlan, String> col5 = new TableColumn<>("Departure\nDestination");
        col5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightPlan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightPlan, String> f) {
                FlightPlan flightPlan = f.getValue();
                String out = flightPlan.getDeparture() +"\n" + flightPlan.getDestination();

                return new SimpleStringProperty(out);
            }
        });
        flightsTable.getColumns().add(col5);
        col5.setPrefWidth(130);

        TableColumn<FlightPlan, Integer> col7 = new TableColumn<>("Distance");
        col7.setCellValueFactory(new PropertyValueFactory<>("distance"));
        flightsTable.getColumns().add(col7);
        col7.setPrefWidth(70);


        TableColumn<FlightPlan, String> col8 = new TableColumn<>("Fuel Consumed");
        col8.setCellValueFactory(new PropertyValueFactory<>("fuelConsumed"));
        flightsTable.getColumns().add(col8);
        col8.setPrefWidth(120);

        TableColumn<FlightPlan, String> col9 = new TableColumn<>("Fuel Remaining");
        col9.setCellValueFactory(new PropertyValueFactory<>("fuelRemaining"));
        flightsTable.getColumns().add(col9);
        col9.setPrefWidth(120);

        TableColumn<FlightPlan, Integer> col10 = new TableColumn<>("Terminated At");
        col10.setCellValueFactory(new PropertyValueFactory<>("terminatedAt"));
        flightsTable.getColumns().add(col10);
        col10.setPrefWidth(120);

        TableColumn<FlightPlan, String> col11 = new TableColumn<>("Time Remaining\nin Seconds");
        col11.setCellValueFactory(new PropertyValueFactory<>("timeRemainingInSeconds"));
        flightsTable.getColumns().add(col11);
        col11.setPrefWidth(120);

    }


    public void currentFlightPlanView(Scene scene){

        BorderPane borderPaneLoggedIn = (BorderPane) scene.getRoot();

        String out = spaceTraderEngine.listMyShips();

        if (out.equals("ok")){

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.fitToWidthProperty().set(true);
            borderPaneLoggedIn.setCenter(scrollPane);

            VBox shipBox = new VBox(20);
            shipBox.setAlignment(Pos.TOP_CENTER);
            scrollPane.setContent(shipBox);
            shipBox.setPadding(new Insets(0, 0, 50, 0));


            TableView flightsTable = new TableView();
            TableView.TableViewSelectionModel selectionModel = flightsTable.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            flightsTable.getStylesheets().add("Ships.css");

            Button refreshTable = new Button("Refresh Flight Table");
            refreshTable.setOnAction((ActionEvent e) -> {

                int selected = selectionModel.getSelectedIndex();

                flightsTable.getItems().clear();

                List<FlightPlan> flightPlans = spaceTraderEngine.getCurrentFlights();

                for (FlightPlan flightPlan: flightPlans){
                    flightsTable.getItems().add(flightPlan);
                }

                selectionModel.select(selected);
            });
            shipBox.getChildren().addAll(refreshTable, flightsTable);


            setUpMyFlightsTable(flightsTable);

//            flightsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


            List<FlightPlan> flightPlans = spaceTraderEngine.getCurrentFlights();

            for (FlightPlan flightPlan: flightPlans){
                flightsTable.getItems().add(flightPlan);
            }

        } else {
            Label errorMsg = new Label(out);
            borderPaneLoggedIn.setCenter(errorMsg);
            errorMsg.setId("failure");
        }

    }

}
