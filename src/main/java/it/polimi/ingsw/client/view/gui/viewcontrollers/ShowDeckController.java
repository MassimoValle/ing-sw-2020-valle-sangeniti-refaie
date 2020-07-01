package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.model.gods.PumpedDeck;
import it.polimi.ingsw.client.view.gui.ParameterListener;
import it.polimi.ingsw.client.view.gui.viewcontrollers.helper.GodInfoLabel;
import it.polimi.ingsw.server.model.god.God;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowDeckController implements Initializable {

    @FXML
    private FlowPane godsFlowPane;

    @FXML
    private AnchorPane showDeckAnchorPane;


    ParameterListener parameterListener = ParameterListener.getInstance();
    private String parameter = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientManager.LOGGER.info("ShowDeckController created!");

        Stage stage = GUI.getStage();
        stage.setWidth(1280);
        stage.setHeight(720);

        PumpedDeck pumpedDeck = (PumpedDeck) BabyGame.getInstance().getDeck();

        for (God god : pumpedDeck){
            ImageView imageView = god.getImgView();


            AnchorPane pane = new AnchorPane(imageView);
            pane.setId(god.getGodName());

            imageView.fitHeightProperty().bind(showDeckAnchorPane.heightProperty().divide(3.5));
            imageView.fitWidthProperty().bind(showDeckAnchorPane.widthProperty().divide(6));

            AnchorPane.setTopAnchor(imageView, 0.0);
            AnchorPane.setLeftAnchor(imageView, 0.0);
            AnchorPane.setRightAnchor(imageView, 0.0);
            AnchorPane.setBottomAnchor(imageView, 0.0);

            pane.setOnMouseClicked(event -> {
                AnchorPane pane1 = (AnchorPane) event.getSource();
                parameter = pane1.getId();
                pane1.setDisable(true);
                pane1.setOpacity(0.7);
                parameterListener.setParameter(parameter);
            });

            GodInfoLabel godInfoLabel = new GodInfoLabel(god);
            pane.getChildren().add(godInfoLabel);

            godsFlowPane.getChildren().add(pane);

        }

    }
}
