package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.model.Building.*;
import it.polimi.ingsw.server.model.player.ColorEnum;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;
import it.polimi.ingsw.utility.*;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Square {

    protected final int row;
    protected final int column;

    //To keep trace of worker is on the square
    protected Worker workerOnSquare;
    protected ArrayList<Block> tower;

    //GUI
    private ImageView imgWorker;
    private StackPane stackPane;
    private boolean GUI = false;



    public Square(int row, int column) {
        this.tower = null;
        this.workerOnSquare = null;
        this.row = row;
        this.column = column;
    }

    public void initGUIObj(){
        GUI = true;
        imgWorker = null;
        stackPane = new StackPane();
    }

    public Worker getWorkerOnSquare() {
        if (hasWorkerOn()) {
            return this.workerOnSquare;
        } else {
            return null;
        }
    }

    public int getHeight() {
        if (tower == null)
            return 0;
        else
            return tower.size();
    }

    public int getRow() {return this.row;}
    public int getColumn() {return this.column;}

    public boolean hasWorkerOn() {
        return workerOnSquare != null;
    }

    /**
     *  Checks if any kind of {@link Block} has been placed over
     *
     * @return true if {@link #tower} still not initalized
     */
    public boolean hasBeenBuiltOver() {
        return tower != null;
    }

    public Position getPosition() {
        return new Position(this.row, this.column);
    }

    public boolean hasDome(){

        if (tower == null) return false;

        for(Block b : tower) {
            if (b instanceof Dome) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets {@link Worker} on {@link #workerOnSquare}
     *
     * @param worker the worker
     */
    public void setWorkerOn(Worker worker) {

        this.workerOnSquare = worker;

            if (GUI) {

                Platform.runLater(() -> {
                    imgWorker = worker.getImgView();
                    stackPane.getChildren().add(imgWorker);
                });

            }
    }

    /**
     * Sets the square free from the previously worker placed
     */
    public void freeSquare() {

        if (GUI) {

            Platform.runLater(() -> {
                getStackPane().getChildren().remove(imgWorker);
                imgWorker = null;
            });

        }

        this.workerOnSquare = null;
    }



    /**
     * Add a {@link Block} to the {@link #tower}
     *
     * @param dome true if the block we want to add is a {@link Dome}
     */
    public void addBlock(boolean dome) throws DomePresentException {

        if (this.hasDome()) {
            throw new DomePresentException();
        }

        if (tower == null) {
            tower = new ArrayList<>();
        }

        if (dome) {
            Dome dome1 = new Dome();
            tower.add(dome1);

            if(GUI) {
                Platform.runLater(() -> addIfGUI(dome1));
            }
        } else {
            switch (tower.size()){
                case 0:
                    LevelOneBlock levelOneBlock = new LevelOneBlock();
                    tower.add(levelOneBlock);

                    if(GUI) {
                        Platform.runLater(() -> addIfGUI(levelOneBlock));
                    }

                    break;
                case 1:
                    LevelTwoBlock levelTwoBlock = new LevelTwoBlock();
                    tower.add(levelTwoBlock);

                    if(GUI) {
                        Platform.runLater(() -> addIfGUI(levelTwoBlock));
                    }

                    break;
                case 2:
                    LevelThreeBlock levelThreeBlock = new LevelThreeBlock();
                    tower.add(levelThreeBlock);

                    if(GUI) {
                        Platform.runLater(() -> addIfGUI(levelThreeBlock));
                    }
                    break;
                case 3:
                    Dome dome1 = new Dome();
                    tower.add(dome1);

                    if(GUI) {
                        Platform.runLater(() -> addIfGUI(dome1));
                    }
                    break;
                default:
                    //throw new Exception();
            }
        }

    }

    private void addIfGUI(Block block){
        block.initGUIObj();
        if (imgWorker == null) {
            if(!getStackPane().getChildren().isEmpty())
                getStackPane().getChildren().remove(0);

            getStackPane().getChildren().add(block.getImageView());
        }
        else {
            switchOnTop(block);
        }
    }

    private void switchOnTop(Block block){
        getStackPane().getChildren().remove(imgWorker);
        getStackPane().getChildren().add(block.getImageView());
        getStackPane().getChildren().add(imgWorker);
    }




    //testing-only
    public ArrayList<Block> _getTower() {
        return this.tower;
    }

    @Override
    public String toString() {
        String ret = "";

        if (getHeight() != 0) {
            if (tower.size() == 4) {
                return "F      ";
            }else if(hasDome()){
                return "D      ";
            }
        }


        switch (getHeight()) {
            case 0 -> ret = "";
            case 1 -> ret = "1    ";
            case 2 -> ret = "2    ";
            case 3 -> ret = "3    ";
            default -> {
                //null
            }
        }

        if (workerOnSquare != null){
            ret += addWorkerColor(workerOnSquare);
            if (getHeight() != 0)
                return ret;
            //ret += "X";
        }
        ret += "\t";
        return ret;
    }

    private String addWorkerColor(Worker workerOnSquare) {

        final String ANSI_RESET = "\u001B[0m";

        ColorEnum color = workerOnSquare.getColor();
        String str = "";
        if (ColorEnum.RED.equals(color)) {
            str = Ansi.RED.concat(" W" + ANSI_RESET);
        } else if (ColorEnum.BLUE.equals(color)) {
            str = Ansi.BLUE.concat(" W" + ANSI_RESET);
        } else if (ColorEnum.GREEN.equals(color)) {
            str = Ansi.GREEN.concat(" W" + ANSI_RESET);
        }

        return str;
    }


    public boolean isFull() {
        return this.getHeight() == 4;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Square)) {
            return false;
        }

        Square square = (Square) obj;

        return this.row == square.row &&
                this.column == square.column &&
                this.workerOnSquare.equals(square.workerOnSquare) &&
                this.getHeight() == square.getHeight();
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getColumn();
        result = 31 * result + (getWorkerOnSquare() != null ? getWorkerOnSquare().hashCode() : 0);
        result = 31 * result + (tower != null ? tower.hashCode() : 0);
        return result;
    }

    public StackPane getStackPane() {
        return stackPane;
    }
}


