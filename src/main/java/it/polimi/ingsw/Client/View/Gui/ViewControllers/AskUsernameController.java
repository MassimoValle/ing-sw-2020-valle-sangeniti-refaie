package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Server.View.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AskUsernameController  extends Observable<String> implements Initializable {


    private static AskUsernameController instance = null;

    public static AskUsernameController getInstance(){
        if(instance == null)
            instance = new AskUsernameController();
        return instance;
    }

    @FXML
    private TextField txtFieldUsername;

    private String username = null;

    @FXML
    void getUsernameEvent(ActionEvent event) {
        username = txtFieldUsername.getText();
        notify(username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AskUsernameController created!");
    }
}
