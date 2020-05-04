package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Model.Building.*;
import it.polimi.ingsw.Model.Player.Worker;

import java.util.ArrayList;

public class Square {

    //To keep trace of worker is on the square
    private Worker workerOnSquare;
    private ArrayList<Block> tower;


    public Square() {
        tower = null;
        this.workerOnSquare = null;
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
                return "D";
            }
        }


        switch (getHeight()) {
            case 0 -> ret = "0";
            case 1 -> ret = "1";
            case 2 -> ret = "2";
            case 3 -> ret = "3";
            case 4 -> ret = "4";
        }

        if (workerOnSquare != null){
            ret += "X";
        }
        return ret;
    }


}


