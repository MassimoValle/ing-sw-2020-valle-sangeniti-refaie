package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.view.gui.viewcontrollers.helper.GodInfoLabel;
import it.polimi.ingsw.client.view.gui.viewcontrollers.helper.GodPane;
import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.god.God;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PickGodController implements Initializable {

    @FXML
    private FlowPane godsFlowPane;

    @FXML
    private AnchorPane pickGodAnchorPane;

    private ArrayList<God> hand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientManager.LOGGER.info("PickGodController created!");
    }
    
    public void setHand(List<God> hand){

        this.hand = (ArrayList<God>) hand;

        // usato per far preparare a javafx il container prima di lavorarci
        Platform.runLater(this::setScene);
    }

    private void setScene() {

        Stage stage = GUI.getStage();
        stage.setWidth(1280);
        stage.setHeight(720);

        ArrayList<God> pumpedHand = new ArrayList<>();
        Deck deck = BabyGame.getInstance().getDeck();

        for (God god : hand){
            pumpedHand.add(deck.getGodByName(god.getGodName()));
        }

        for (God god : pumpedHand){
            ImageView imageView = god.getImgView();

            GodPane godPane = new GodPane(pickGodAnchorPane, imageView, god);

            GodInfoLabel godInfoLabel = new GodInfoLabel(god);
            godPane.getChildren().add(godInfoLabel);

            godsFlowPane.getChildren().add(godPane);
        }
    }
}
