package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Server.View.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AskLobbySizeController  extends Observable<String> implements Initializable {

    private static AskLobbySizeController instance = null;

    public static AskLobbySizeController getInstance(){
        if(instance == null)
            instance = new AskLobbySizeController();
        return instance;
    }

    @FXML
    private TextField txtFieldIP;

    private String parameter = null;

    @FXML
    void getLobbySizeEvent(ActionEvent event) {

        parameter = txtFieldIP.getText();
        notify(parameter);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AskLobbySizeController created!");
    }
}
