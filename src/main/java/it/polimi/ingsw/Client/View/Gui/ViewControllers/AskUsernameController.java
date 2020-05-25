package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.View.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AskUsernameController implements Initializable {

    ParameterListener parameterListener = ParameterListener.getInstance();

    @FXML
    private TextField txtFieldUsername;

    private String username = null;

    @FXML
    void getUsernameEvent(ActionEvent event) {
        username = txtFieldUsername.getText();
        parameterListener.setParameter(username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AskUsernameController created!");
    }
}
