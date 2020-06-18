package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GodInfoController implements Initializable {

    @FXML
    private AnchorPane infoAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("GodInfoController created!");
    }


    public void setGodInfo(String godName){
        String path = "/imgs/God_info/" + godName + ".jpg";

        Platform.runLater(() ->
                infoAnchorPane.setStyle("-fx-background-image: url("+path+")")
                );



    }
}
