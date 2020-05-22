package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Server.View.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AskIpAddressController extends Observable<String> implements Initializable {

    private static AskIpAddressController instance = null;

    public static AskIpAddressController getInstance(){
        if(instance == null)
            instance = new AskIpAddressController();
        return instance;
    }

    String ipAddress = null;

    @FXML
    private TextField txtFieldIP;


    @FXML
    void getIpEvent(ActionEvent event) {

        ipAddress = txtFieldIP.getText();
        notify(ipAddress);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AskIpAddressController created!");
    }
}
