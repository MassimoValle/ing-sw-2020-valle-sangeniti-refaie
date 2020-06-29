package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    void newGameEvent(ActionEvent event) {
        GUI.startGame();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("WelcomeController created!");
    }
}
