package it.polimi.ingsw.client.view.gui.viewcontrollers;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.model.map.GUImap;
import it.polimi.ingsw.client.view.gui.ParameterListener;
import it.polimi.ingsw.client.view.gui.viewcontrollers.helper.PlayerBox;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;


public class MainViewController implements Initializable {



    @FXML
    private GridPane gridPane;

    @FXML
    private StackPane boardStackPane;

    @FXML
    private AnchorPane topLayer;

    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox2;

    @FXML
    private VBox vbox3;

    @FXML
    private Button powerButton;

    @FXML
    private Label gamePhase;



    GUImap guiMap = (GUImap) BabyGame.getInstance().getClientMap();
    private Set<Player> players;

    ParameterListener parameterListener = ParameterListener.getInstance();
    private Object parameter = null;

    //private ArrayList<AnchorPane> enablePosition;


    private void setupImageView(){

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 5; col++) {

                AnchorPane anchorPane = (AnchorPane) getNodeByRowColumnIndex(row, col);

                anchorPane.getChildren().add((guiMap.getSquare(row,col)).getStackPane());

                anchorPane.setOnMouseClicked(e -> {

                    int x = GridPane.getRowIndex((Node) e.getSource());
                    int y = GridPane.getColumnIndex((Node) e.getSource());

                    parameter = new Position(x, y);
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
    void powerEvent() {
        parameterListener.setParameter("power");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("MainViewController created!");

    }


    public void setScene() {

        Stage stage = GUI.getStage();

        stage.setResizable(false);
        stage.setWidth(1280);
        stage.setHeight(720);

        topLayer.setPickOnBounds(false);
        powerButton.setDisable(true);
        boardStackPane.setMinSize(0.0 , 0.0);


        int counter = 1;

        for (Player player : players){

            switch (counter) {

                case 1 -> new PlayerBox(vbox1, player);
                case 2 -> new PlayerBox(vbox2, player);
                case 3 -> new PlayerBox(vbox3, player);

                default -> System.out.println("error");
            }

            counter++;



        }

        Platform.runLater(this::setupImageView);
    }

    public void changePhase(String phase){
        Platform.runLater(() -> {
            gamePhase.getStyleClass().remove(0);
            gamePhase.getStyleClass().add(phase);
        });
    }


    public void enablePowerButton(){
        powerButton.setDisable(false);
    }

    public void disablePowerButton(){

        if(!powerButton.isDisable())
            powerButton.setDisable(true);
    }

    /*public void enablePosition(ArrayList<Position> nearlyPosValid) {

        for(Position pos : nearlyPosValid) {
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == pos.getColumn() && GridPane.getRowIndex(node) == pos.getRow()) {
                    AnchorPane ap = (AnchorPane) node;

                    ap.setStyle("-fx-background-color: yellow");
                    ap.setStyle("-fx-opacity: 20%");

                    enablePosition.add(ap);
                }
            }
        }

    }

     public void disablePosition(){

        for(AnchorPane ap : enablePosition){

            ap.setStyle("-fx-background-color: transparent");
            enablePosition.remove(ap);

        }

    }
    */

}
