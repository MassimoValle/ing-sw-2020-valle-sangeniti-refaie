package it.polimi.ingsw.client.view.gui.viewcontrollers.helper;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.viewcontrollers.GodInfoController;
import it.polimi.ingsw.server.model.god.God;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GodInfoLabel extends AnchorPane {

    public GodInfoLabel(God god){

        // info
        this.setId(god.getGodName());

        ImageView imageView1 = new ImageView(new Image("/imgs/info.png"));
        this.getChildren().add(imageView1);

        AnchorPane.setTopAnchor(this, 5.0);
        AnchorPane.setRightAnchor(this, 5.0);


        this.setOnMouseClicked(event -> {

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

    }
}
