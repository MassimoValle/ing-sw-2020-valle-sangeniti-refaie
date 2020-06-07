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

import java.util.ArrayList;

public class PumpedSquare extends Square {

    private Image baseImg;
    private ImageView imgView;
    private Image workerImg;
    protected PumpedWorker workerOnSquare;

    public PumpedSquare(int row, int column, Image baseImg) {
        super(row, column);
        this.baseImg = baseImg;

        this.imgView  = new ImageView(baseImg);
    }

    public ImageView getImg() {
        return imgView;
    }

    private void setImg(Image img) {
        this.baseImg = img;
        this.imgView.setImage(baseImg);
    }

    @Override
    public void setWorkerOn(Worker worker) {

        this.workerOnSquare = (PumpedWorker) worker;

        workerImg = workerOnSquare.getImg();
        this.imgView.setImage(workerImg);

    }

    /**
     * Sets the square free from the previously worker placed
     */
    @Override
    public void freeSquare() {

        this.workerOnSquare = null;
        setImg(baseImg);

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
                    tower.add(new PumpedLevelOneBlock());
                    break;
                case 1:
                    tower.add(new PumpedLevelTwoBlock());
                    break;
                case 2:
                    tower.add(new PumpedLevelThreeBlock());
                    break;
                case 3:
                    tower.add(new PumpedDome());
                    break;
                default:
                    //throw new Exception();
            }
        }

    }
}
