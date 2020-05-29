package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.GUImain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    void newGameEvent(ActionEvent event) {
        GUImain.startGame();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("WelcomeController created!");
    }
}
