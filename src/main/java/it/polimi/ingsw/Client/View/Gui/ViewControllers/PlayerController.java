package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Server.Model.Player.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private Label playerName;

    @FXML
    private Label playerGodName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("PlayerController created!");
    }


    public void init(Player player){
        playerName.setText(player.getPlayerName());
        playerGodName.setText(player.getPlayerGod().getGodName());
    }
}
