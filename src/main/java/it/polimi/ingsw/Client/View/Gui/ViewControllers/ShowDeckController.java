package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.Model.Gods.PumpedDeck;
import it.polimi.ingsw.Client.Model.Gods.PumpedGod;
import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.Model.God.God;
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


            // info
            AnchorPane info = new AnchorPane();
            info.setId(god.getGodName());
            pane.getChildren().add(info);
            AnchorPane.setTopAnchor(info, 5.0);
            AnchorPane.setRightAnchor(info, 5.0);

            info.setOnMouseClicked(event -> {
                AnchorPane pane1 = (AnchorPane) event.getSource();
                Stage newWindow = new Stage();

            });

            godsFlowPane.getChildren().add(pane);

        }

    }
}
