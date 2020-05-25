package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.View.Cli.CLI;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Client.View.Gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GUImain extends Application {

    private static Scene scene;
    private static ClientView clientView;
    private static FXMLLoader loader;

    @Override
    public void start(Stage stage) throws Exception {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Welcome.fxml"));
        Parent root = loader.load();

        scene = new Scene(root);*/

        setRoot("welcome", null);

        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();

        clientView = new GUI();

    }



    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void startGame(){
        Thread myThread = new Thread(clientView);
        myThread.start();
    }

    public static void setRoot(String fxml, ResourceBundle resourceBundle) throws IOException {
        Parent root = loadFXML(fxml, resourceBundle);

        if(scene == null) scene = new Scene(root);
        else scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml, ResourceBundle resourceBundle) throws IOException {
        loader = new FXMLLoader(GUImain.class.getResource("/fxml/" + fxml + ".fxml"), resourceBundle);
        return loader.load();
    }

    public static FXMLLoader getFXMLLoader(){
        return loader;
    }

}
