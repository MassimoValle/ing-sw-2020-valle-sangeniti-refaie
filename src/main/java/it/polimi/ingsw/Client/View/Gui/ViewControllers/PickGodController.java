package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.Model.PumpedDeck;
import it.polimi.ingsw.Client.Model.PumpedGod;
import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.Model.God.God;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PickGodController implements Initializable {

    @FXML
    private FlowPane godsFlowPane;

    private String parameter = null;

    ParameterListener parameterListener = ParameterListener.getInstance();
    private ArrayList<God> hand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("PickGodController created!");
    }
    
    public void setHand(ArrayList<God> hand){

        this.hand = hand;

        // usato per far preparare a javafx il container prima di lavorarci
        Platform.runLater(this::setScene);
    }

    private void setScene() {

        ArrayList<God> pumpedHand = new ArrayList<>();

        for (God god : hand){
            pumpedHand.add(PumpedDeck.getInstance().getGodByName(god.getGodName()));
        }

        for (God god : pumpedHand){
            ImageView imageView = ((PumpedGod)god).getImg();

            imageView.setFitWidth(100);
            imageView.setFitHeight(200);

            AnchorPane pane = new AnchorPane(imageView);
            pane.setId(god.getGodName());

            AnchorPane.setTopAnchor(imageView, 0.0);
            AnchorPane.setLeftAnchor(imageView, 0.0);
            AnchorPane.setRightAnchor(imageView, 0.0);
            AnchorPane.setBottomAnchor(imageView, 0.0);

            pane.setOnMouseClicked(event -> {
                AnchorPane pane1 = (AnchorPane) event.getSource();
                parameter = pane1.getId();
                pane1.setDisable(true);
                pane1.setOpacity(0.5);
                parameterListener.setParameter(parameter);
            });

            godsFlowPane.getChildren().add(pane);
        }
    }
}
