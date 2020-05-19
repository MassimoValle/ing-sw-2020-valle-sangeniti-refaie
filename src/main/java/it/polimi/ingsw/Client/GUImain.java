package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.View.Cli.CLI;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Client.View.Gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GUImain extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ClientView clientView = new GUI();
        clientView.start();



        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("GUI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
