

import javafx.application.Application;
import javafx.stage.Stage;

public class SpaceTraderApp extends Application {

    private static SpaceTraderEngine spaceTraderEngine;
    private static SpaceTraderView spaceTraderView;

    @Override
    public void start(Stage stage){
        spaceTraderView.logInSetUp(stage);
    }


    public static void main(String[] args) {

       boolean online;

        if (args.length == 1){
            if (args[0].equals("offline")) {
                online = false;
            } else if (args[0].equals("online")) {
                online = true;
            } else {
                return;
            }
        } else {
            System.out.println("invalid");
            return;
        }

        spaceTraderEngine = new SpaceTraderEngine(online);
        spaceTraderView = new SpaceTraderView(spaceTraderEngine);

        launch();
    }


}
