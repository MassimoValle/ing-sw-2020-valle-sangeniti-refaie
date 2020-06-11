package it.polimi.ingsw.Client.View.Gui.ViewControllers;

import it.polimi.ingsw.Client.GUImain;
import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.Map.GUImap;
import it.polimi.ingsw.Client.Model.Map.PumpedSquare;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

                AnchorPane anchorPane = new AnchorPane();
                ImageView imageView = ((PumpedSquare) guiMap.getSquare(row, col)).getImg();

                ImageViewPane imageViewPane = new ImageViewPane(imageView);

                AnchorPane.setTopAnchor(imageViewPane, 3.0);
                AnchorPane.setLeftAnchor(imageViewPane, 3.0);
                AnchorPane.setRightAnchor(imageViewPane, 3.0);
                AnchorPane.setBottomAnchor(imageViewPane, 3.0);

                String baseBg = "FFFFFF";
                anchorPane.setStyle("-fx-background-color: #" + baseBg);

                anchorPane.getChildren().add(imageViewPane);

                anchorPane.setOnMouseClicked(e -> {
                    //TODO
                    int x = GridPane.getRowIndex((Node) e.getSource());
                    int y = GridPane.getColumnIndex((Node) e.getSource());

                    Position ret = new Position(x, y);

                    parameter = ret;
                    parameterListener.setParameter(parameter);

                });

                gridPane.add(anchorPane, row, col);

            }

        }
    }


    public void setPlayers(Set<Player> playerSet){
        this.players = playerSet;
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private Node standardGetElementAt(int i, int j) {

        for (Node x : gridPane.getChildren()) {

            if(gridPane == null) System.out.println("gridPane");
            if(x == null) System.out.println("x");

            int gridPaneX = GridPane.getRowIndex(x);
            int gridPaneY = GridPane.getColumnIndex(x);

            if ((gridPaneX == i) && (gridPaneY == j)) {
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


    private void setScene() {

        Stage stage = GUImain.getStage();
        stage.setMaximized(true);

        // prove di binding
        /*
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
        */


        for (Player player : players){

            /*
            FXMLLoader loader = null;
            Parent node = null;
            try {

                loader = new FXMLLoader(GUImain.class.getResource("/fxml/" + "player" + ".fxml"));
                node = loader.load();

            }catch (IOException e){
                e.printStackTrace();
            }

            hboxPlayers.getChildren().add(node);

            PlayerController controller = loader.getController();
            controller.init(player);
            */


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
