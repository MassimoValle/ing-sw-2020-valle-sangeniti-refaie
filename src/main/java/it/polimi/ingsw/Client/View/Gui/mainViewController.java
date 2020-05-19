package it.polimi.ingsw.Client.View.Gui;

import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.GUImap;
import it.polimi.ingsw.Client.Model.PumpedSquare;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


public class mainViewController implements Initializable {

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

    //ClientGameMap clientGameMap = ClientGameMap.getIstance();

    GUImap guImap = (GUImap) BabyGame.getInstance().clientMap;


    private void setupImageView(){

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 5; col++) {

                AnchorPane anchorPane = (AnchorPane) standardGetElementAt(row, col);

                ImageView imageView = ((PumpedSquare)guImap.getSquare(row, col)).getImg();

                AnchorPane.setTopAnchor(imageView, 0.0);
                AnchorPane.setLeftAnchor(imageView, 0.0);
                AnchorPane.setRightAnchor(imageView, 0.0);
                AnchorPane.setBottomAnchor(imageView, 0.0);

                assert anchorPane != null;
                anchorPane.getChildren().add(imageView);
                imageView.fitWidthProperty().bind(anchorPane.widthProperty());

            }

        }
    }

    private Node standardGetElementAt(int i, int j) {

        for (Node x : gridPane.getChildren()) {
            if ((GridPane.getRowIndex(x) == i) && (GridPane.getColumnIndex(x) == j)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupImageView();
    }
}
