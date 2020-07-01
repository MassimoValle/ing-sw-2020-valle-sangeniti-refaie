package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.ParameterListener;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AskUsernameController implements Initializable {



    @FXML
    private TextField txtFieldUsername;

    @FXML
    private Button getUsernameButton;

    ParameterListener parameterListener = ParameterListener.getInstance();

    @FXML
    void getUsernameEvent() {
        String username = txtFieldUsername.getText();
        parameterListener.setParameter(username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientManager.LOGGER.info("AskUsernameController created!");

        getUsernameButton.disableProperty().bind(
                Bindings.isEmpty(txtFieldUsername.textProperty())
        );
    }
}
