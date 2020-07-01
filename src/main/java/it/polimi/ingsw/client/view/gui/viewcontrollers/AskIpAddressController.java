package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.ParameterListener;
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
    void getIpEvent() {
        String ipAddress = txtFieldIP.getText();
        parameterListener.setParameter(ipAddress);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ClientManager.LOGGER.info("AskIpAddressController created!");
    }
}
