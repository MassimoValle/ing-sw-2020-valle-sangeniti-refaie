package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.model.gods.PumpedDeck;
import it.polimi.ingsw.client.view.gui.ParameterListener;
import it.polimi.ingsw.server.model.god.God;
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

            // info
            AnchorPane ap_info = new AnchorPane();
            ap_info.setId(god.getGodName());

            ImageView imageView1 = new ImageView(new Image("/imgs/info.png"));
            ap_info.getChildren().add(imageView1);

            pane.getChildren().add(ap_info);
            AnchorPane.setTopAnchor(ap_info, 5.0);
            AnchorPane.setRightAnchor(ap_info, 5.0);


            ap_info.setOnMouseClicked(event -> {

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
