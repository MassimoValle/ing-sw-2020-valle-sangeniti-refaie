package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.view.gui.ParameterListener;
import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.god.God;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

            // info
            AnchorPane apInfo = new AnchorPane();
            apInfo.setId(god.getGodName());

            ImageView imageView1 = new ImageView(new Image("/imgs/info.png"));
            apInfo.getChildren().add(imageView1);

            pane.getChildren().add(apInfo);
            AnchorPane.setTopAnchor(apInfo, 5.0);
            AnchorPane.setRightAnchor(apInfo, 5.0);


            apInfo.setOnMouseClicked(event -> {

                event.consume();

                AnchorPane pane1 = (AnchorPane) event.getSource();

                String godName = pane1.getId();

                Stage newWindow = new Stage();


                FXMLLoader loader = new FXMLLoader(GUI.class.getResource("/fxml/infoGod.fxml"));
                Parent root = null;

                try {
                    root = loader.load();
                } catch (IOException exception) {
                    ClientManager.LOGGER.severe(exception.getMessage());
                }

                assert root != null;
                Scene scene = new Scene(root);

                newWindow.setTitle("God info");
                newWindow.setScene(scene);
                newWindow.show();

                GodInfoController controller = loader.getController();
                controller.setGodInfo(godName);
            });

            godsFlowPane.getChildren().add(pane);
        }
    }
}
