package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.client.view.ClientView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GUI extends Application {

    private static Scene scene;
    private static ClientView clientView;
    private static FXMLLoader loader;
    private static Stage mystage;

    @Override
    public void start(Stage stage) {

        mystage = stage;

        setRoot("welcome");

        stage.setTitle("Santorini");
        stage.setScene(scene);
        stage.show();

        clientView = new GuiController();

    }


    public static void startGame(){
        Thread myThread = new Thread(clientView);
        myThread.start();
    }

    public static void setRoot(String fxml) {

        Parent root = null;
        try {
            root = loadFXML(fxml);
        } catch (IOException exception) {
            //Logger.getLogger("santorini_log").log(Level.SEVERE, "Fatal error!", exception);
            Thread.currentThread().interrupt();

            //restart
            Application.launch(GUI.class);
        }

        if(scene == null) {
            assert root != null;
            scene = new Scene(root);
        }
        else {
            Parent finalRoot = root;
            Platform.runLater(() -> scene.setRoot(finalRoot));
        }


    }

    private static Parent loadFXML(String fxml) throws IOException {
        loader = new FXMLLoader(GUI.class.getResource("/fxml/" + fxml + ".fxml"), null);
        return loader.load();
    }

    public static FXMLLoader getFXMLLoader(){
        return loader;
    }

    public static Stage getStage(){
        return mystage;
    }

}
