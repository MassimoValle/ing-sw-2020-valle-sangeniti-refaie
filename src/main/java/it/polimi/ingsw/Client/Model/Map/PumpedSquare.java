package it.polimi.ingsw.Client.Model.Map;

import it.polimi.ingsw.Client.Model.Block.PumpedDome;
import it.polimi.ingsw.Client.Model.Block.PumpedLevelOneBlock;
import it.polimi.ingsw.Client.Model.Block.PumpedLevelThreeBlock;
import it.polimi.ingsw.Client.Model.Block.PumpedLevelTwoBlock;
import it.polimi.ingsw.Client.Model.Player.PumpedWorker;
import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Building.Dome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class PumpedSquare extends Square {

    private Image baseImg;
    private ImageView imgWorker;
    public StackPane stackPane;

    public PumpedSquare(int row, int column, Image baseImg) {
        super(row, column);
        this.baseImg = baseImg;
        stackPane = new StackPane();

        ImageView imgView  = new ImageView(baseImg);
        stackPane.getChildren().add(imgView);
    }

    public StackPane getStackPane() {
        return stackPane;
    }


    @Override
    public void setWorkerOn(Worker worker) {

        this.workerOnSquare = worker;

        imgWorker = new ImageView(((PumpedWorker) worker).getImg());
        stackPane.getChildren().add(imgWorker);
    }

    /**
     * Sets the square free from the previously worker placed
     */
    @Override
    public void freeSquare() {

        this.workerOnSquare = null;
        stackPane.getChildren().remove(imgWorker);

    }

    @Override
    public void addBlock(boolean dome) throws DomePresentException {

        if (this.hasDome()) {
            throw new DomePresentException();
        }

        if (tower == null) {
            tower = new ArrayList<>();
        }

        if (dome) {
            tower.add(new Dome());
        } else {
            switch (tower.size()){
                case 0:
                    PumpedLevelOneBlock pumpedLevelOneBlock = new PumpedLevelOneBlock();
                    tower.add(pumpedLevelOneBlock);
                    stackPane.getChildren().add(pumpedLevelOneBlock.getImgView());
                    break;
                case 1:
                    PumpedLevelTwoBlock pumpedLevelTwoBlock = new PumpedLevelTwoBlock();
                    tower.add(pumpedLevelTwoBlock);
                    stackPane.getChildren().add(pumpedLevelTwoBlock.getImgView());
                    break;
                case 2:
                    PumpedLevelThreeBlock pumpedLevelThreeBlock = new PumpedLevelThreeBlock();
                    tower.add(pumpedLevelThreeBlock);
                    stackPane.getChildren().add(pumpedLevelThreeBlock.getImgView());
                    break;
                case 3:
                    PumpedDome pumpedDome = new PumpedDome();
                    tower.add(pumpedDome);
                    stackPane.getChildren().add(pumpedDome.getImgView());
                    break;
                default:
                    //throw new Exception();
            }
        }

    }
}
