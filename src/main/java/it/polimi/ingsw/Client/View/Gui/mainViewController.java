package it.polimi.ingsw.Client.View.Gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;



public class mainViewController {

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



    private void setupImageView(){
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {

                AnchorPane anchorPane = (AnchorPane) standardGetElementAt(row, col);

                //javafx.scene.image.Image image = clientGameMap.getImage(row, col);

                ImageView imageView = new ImageView();
                //imageView.setImage(image);

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

}
