package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.view.gui.ParameterListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AskLobbySizeController implements Initializable {


    @FXML
    private TextField txtFieldLobbySize;

    @FXML
    private ChoiceBox<String> choiceBox;

    ParameterListener parameterListener = ParameterListener.getInstance();

    @FXML
    void getLobbySizeEvent(ActionEvent event) {

        Button btn = (Button) event.getSource();
        btn.setDisable(true);

        Integer parameter = Integer.parseInt(choiceBox.getValue());
        parameterListener.setParameter(parameter);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO LOGGER
        //System.out.println("AskLobbySizeController created!");

        // string array
        String[] st = { "2", "3"};

        // create a choiceBox
        choiceBox.setItems(FXCollections.observableArrayList(st));

        choiceBox.setValue("2");
    }
}
