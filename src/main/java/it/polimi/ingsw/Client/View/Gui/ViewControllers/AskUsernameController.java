package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.View.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
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
    private String username = null;

    @FXML
    void getUsernameEvent(ActionEvent event) {
        username = txtFieldUsername.getText();
        parameterListener.setParameter(username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AskUsernameController created!");

        getUsernameButton.disableProperty().bind(
                Bindings.isEmpty(txtFieldUsername.textProperty())
        );
    }
}
