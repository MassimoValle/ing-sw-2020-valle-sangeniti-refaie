package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.View.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AskIpAddressController implements Initializable {

    ParameterListener parameterListener = ParameterListener.getInstance();

    private String ipAddress = null;

    @FXML
    private TextField txtFieldIP;


    @FXML
    void getIpEvent(ActionEvent event) {
        ipAddress = txtFieldIP.getText();
        parameterListener.setParameter(ipAddress);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AskIpAddressController created!");
    }
}
