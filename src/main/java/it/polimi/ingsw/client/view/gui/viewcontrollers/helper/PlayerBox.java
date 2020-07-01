package it.polimi.ingsw.client.view.gui.viewcontrollers.helper;

import it.polimi.ingsw.server.model.player.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PlayerBox {

    public PlayerBox(VBox vbox, Player player){

        Label playerNameLabel = new Label();
        AnchorPane godAnchorPane = new AnchorPane();

        godAnchorPane.setMinSize(200, 250);

        vbox.getChildren().addAll(playerNameLabel, godAnchorPane);

        //playerNameLabel = (Label) vbox.getChildren().get(0);
        playerNameLabel.setText(player.getPlayerName());

        //godAnchorPane = (AnchorPane) vbox.getChildren().get(1);
        godAnchorPane.getStyleClass().add(player.getPlayerGod().getGodName());

        GodInfoLabel godInfoLabel = new GodInfoLabel(player.getPlayerGod());
        godAnchorPane.getChildren().add(godInfoLabel);

    }
}
