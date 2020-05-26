package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.GUImain;
import it.polimi.ingsw.Client.Model.PumpedDeck;
import it.polimi.ingsw.Client.Model.PumpedGod;
import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.Model.God.God;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowDeckController implements Initializable {

    @FXML
    private FlowPane godsFlowPane;

    @FXML
    private AnchorPane showDeckAnchorPane;

    private String parameter = null;

    ParameterListener parameterListener = ParameterListener.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ShowDeckController created!");

        PumpedDeck pumpedDeck = PumpedDeck.getInstance();

        for (God god : pumpedDeck){
            ImageView imageView = ((PumpedGod)god).getImg();


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

            godsFlowPane.getChildren().add(pane);

        }

    }
}
