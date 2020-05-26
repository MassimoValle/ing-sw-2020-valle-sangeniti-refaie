package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.GUImain;
import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.GUImap;
import it.polimi.ingsw.Client.Model.PumpedSquare;
import it.polimi.ingsw.Server.Model.Player.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
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



    GUImap guImap = (GUImap) BabyGame.getInstance().getClientMap();
    private Set<Player> players;


    private void setupImageView(){

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 5; col++) {

                AnchorPane anchorPane = (AnchorPane) standardGetElementAt(row, col);

                ImageView imageView = ((PumpedSquare)guImap.getSquare(row, col)).getImg();

                ImageViewPane imageViewPane = new ImageViewPane(imageView);

                AnchorPane.setTopAnchor(imageViewPane, 5.0);
                AnchorPane.setLeftAnchor(imageViewPane, 5.0);
                AnchorPane.setRightAnchor(imageViewPane, 5.0);
                AnchorPane.setBottomAnchor(imageViewPane, 5.0);

                String baseBg = "FFFFFF";
                assert anchorPane != null;
                anchorPane.setStyle("-fx-background-color: #" + baseBg);

                anchorPane.getChildren().add(imageView);
                imageView.fitWidthProperty().bind(anchorPane.widthProperty());

            }

        }
    }

    public void setPlayers(Set<Player> playerSet){
        this.players = playerSet;
    }

    private Node standardGetElementAt(int i, int j) {

        for (Node x : gridPane.getChildren()) {
            if ((GridPane.getRowIndex(x) == i) && (GridPane.getColumnIndex(x) == j)) {
                return x;
            }
        }
        return null;
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


    private void setScene(){

        Stage stage = GUImain.getStage();
        stage.setMaximized(true);

        double width = stage.getWidth();
        double height = stage.getHeight();

        System.out.println("stage width: " + width);
        System.out.println("stage height: " + height);

        cliffAnchorPane.setMaxHeight(height);
        cliffAnchorPane.setMaxWidth(width);

        cliffAnchorPane.minWidthProperty().bind(stage.widthProperty());
        cliffAnchorPane.minHeightProperty().bind(stage.heightProperty());

        ImageView imageView = (ImageView) leftCliff.getChildren().get(0);
        imageView.fitHeightProperty().bind(leftCliff.heightProperty());
        imageView.fitWidthProperty().bind(leftCliff.widthProperty());

        ImageView imageView1 = (ImageView) topCliff.getChildren().get(0);
        imageView1.fitHeightProperty().bind(topCliff.heightProperty());
        imageView1.fitWidthProperty().bind(topCliff.widthProperty());

        ImageView imageView2 = (ImageView) rightCliff.getChildren().get(0);
        imageView2.fitHeightProperty().bind(rightCliff.heightProperty());
        imageView2.fitWidthProperty().bind(rightCliff.widthProperty());

        ImageView imageView3 = (ImageView) bottomCliff.getChildren().get(0);
        imageView3.fitHeightProperty().bind(bottomCliff.heightProperty());
        imageView3.fitWidthProperty().bind(bottomCliff.widthProperty());


        for (Player player : players){
            Label nameLabel = new Label(player.getPlayerName());
            Label godLabel = new Label(player.getPlayerGod().getGodName());
            VBox vBox = new VBox(nameLabel, godLabel);
            AnchorPane anchorPane = new AnchorPane(vBox);
            anchorPane.getStyleClass().add("nuvolaSpeedy");

            hboxPlayers.getChildren().add(anchorPane);

        }

        Platform.runLater(this::setupImageView);
    }
}
