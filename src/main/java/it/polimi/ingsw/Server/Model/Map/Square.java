package it.polimi.ingsw.Server.Model.Map;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Building.*;
import it.polimi.ingsw.Server.Model.Player.ColorEnum;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import it.polimi.ingsw.Utility.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Square {

    private final int row;
    private final int column;

    //To keep trace of worker is on the square
    private Worker workerOnSquare;
    private ArrayList<Block> tower;


    public Square(int row, int column) {
        this.tower = null;
        this.workerOnSquare = null;
        this.row = row;
        this.column = column;
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
    }

    /**
     * Sets the square free from the previously worker placed
     */
    public void freeSquare() {
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
            tower.add(new Dome());
        } else {
            switch (tower.size()){
                case 0:
                    tower.add(new LevelOneBlock());
                    break;
                case 1:
                    tower.add(new LevelTwoBlock());
                    break;
                case 2:
                    tower.add(new LevelThreeBlock());
                    break;
                case 3:
                    tower.add(new Dome());
                    break;
                default:
                    //throw new Exception();
            }
        }

    }




    //testing-only
    public ArrayList<Block> _getTower() {
        return this.tower;
    }

    @Override
    public String toString() {
        String ret = "";

        if (getHeight() != 0) {
            if (hasDome()) {
                return "O";
            }
        }


        switch (getHeight()) {
            case 0 -> ret = "";
            case 1 -> ret = "1";
            case 2 -> ret = "2";
            case 3 -> ret = "3";
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
            str = Ansi.BLUE.concat(" W" + ANSI_RESET);
        }

        return str;
    }


    public boolean isFull() {
        return this.getHeight() == 4;
    }
}


