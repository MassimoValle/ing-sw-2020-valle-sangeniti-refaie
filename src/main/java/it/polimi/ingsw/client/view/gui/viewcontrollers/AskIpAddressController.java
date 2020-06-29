package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.view.gui.ParameterListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AskIpAddressController implements Initializable {

    ParameterListener parameterListener = ParameterListener.getInstance();

    @FXML
    private TextField txtFieldIP;


    @FXML
    void getIpEvent(ActionEvent event) {
        String ipAddress = txtFieldIP.getText();
        parameterListener.setParameter(ipAddress);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Use a logger //TODO
        //System.out.println("AskIpAddressController created!");
    }
}
