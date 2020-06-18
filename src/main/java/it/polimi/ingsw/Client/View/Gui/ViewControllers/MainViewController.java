package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.GUImain;
import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.Map.GUImap;
import it.polimi.ingsw.Client.View.Gui.ParameterListener;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;


public class MainViewController implements Initializable {

    @FXML
    private AnchorPane mainViewAnchorPane;

    @FXML
    private AnchorPane cliffAnchorPane;

    @FXML
    private AnchorPane topCliff;

    @FXML
    private AnchorPane leftCliff;

    @FXML
    private AnchorPane board;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane rightCliff;

    @FXML
    private AnchorPane bottomCliff;

    @FXML
    private ToggleButton powerButton;

    @FXML
    private HBox hboxPlayers;



    GUImap guiMap = (GUImap) BabyGame.getInstance().getClientMap();
    private Set<Player> players;

    ParameterListener parameterListener = ParameterListener.getInstance();
    private Object parameter = null;

    private ArrayList<AnchorPane> enablePosition;


    private void setupImageView(){

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 5; col++) {

                AnchorPane anchorPane = (AnchorPane) getNodeByRowColumnIndex(row, col);

                anchorPane.getChildren().add((guiMap.getSquare(row,col)).getStackPane());

                anchorPane.setOnMouseClicked(e -> {
                    //TODO
                    int x = GridPane.getRowIndex((Node) e.getSource());
                    int y = GridPane.getColumnIndex((Node) e.getSource());

                    Position ret = new Position(x, y);

                    parameter = ret;
                    parameterListener.setParameter(parameter);

                });


            }

        }
    }


    public void setPlayers(Set<Player> playerSet){
        this.players = playerSet;
    }

    public Node getNodeByRowColumnIndex (final int row, final int column) {

        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {

            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }

        }

        return result;
    }


    @FXML
    void powerEvent(ActionEvent event) {

        Button powerButton = (Button) event.getSource();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("MainViewController created!");

    }

    public void init(){

        Platform.runLater(this::setScene);
    }


    private void setScene() {

        Stage stage = GUImain.getStage();
        //stage.setResizable(false);


        for (Player player : players){

            Label nameLabel = new Label(player.getPlayerName());
            Label godLabel = new Label(player.getPlayerGod().getGodName());
            VBox vBox = new VBox(nameLabel, godLabel);
            AnchorPane anchorPane = new AnchorPane(vBox);

            hboxPlayers.getChildren().add(anchorPane);

        }

        Platform.runLater(this::setupImageView);
    }

    public void enablePosition(ArrayList<Position> nearlyPosValid) {

        for(Position pos : nearlyPosValid) {
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == pos.getColumn() && GridPane.getRowIndex(node) == pos.getRow()) {
                    AnchorPane ap = (AnchorPane) node;

                    ap.setStyle("-fx-background-color: yellow");

                    enablePosition.add(ap);
                }
            }
        }

    }

     public void disablePosition(){

        for(AnchorPane ap : enablePosition){

            ap.setStyle("-fx-background-color: white");
            enablePosition.remove(ap);

        }

    }
}
