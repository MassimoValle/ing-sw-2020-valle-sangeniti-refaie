package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.GUImain;
import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PickGodController implements Initializable {

    @FXML
    private FlowPane godsFlowPane;

    @FXML
    private AnchorPane pickGodAnchorPane;


    ParameterListener parameterListener = ParameterListener.getInstance();
    private String parameter = null;

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

        Stage stage = GUImain.getStage();
        stage.setWidth(1280);
        stage.setHeight(720);

        ArrayList<God> pumpedHand = new ArrayList<>();
        Deck deck = BabyGame.getInstance().getDeck();

        for (God god : hand){
            pumpedHand.add(deck.getGodByName(god.getGodName()));
        }

        for (God god : pumpedHand){
            ImageView imageView = god.getImgView();


            AnchorPane pane = new AnchorPane(imageView);
            pane.setId(god.getGodName());

            imageView.fitHeightProperty().bind(pickGodAnchorPane.heightProperty().divide(3.5));
            imageView.fitWidthProperty().bind(pickGodAnchorPane.widthProperty().divide(4));

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
