package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Building.*;
import it.polimi.ingsw.Model.Player.Worker;

import java.util.ArrayList;

public class Square {
    //To keep trace of whose worker is on the square
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
        return tower.size();
    }

    public  void freeSquare() {
        this.workerOnSquare = null;
    }

    public boolean hasWorkerOn() {
        return workerOnSquare != null;
    }

    public boolean hasBeenBuiltOver() {
        if(tower == null) return true;
        return false;
    }

    public boolean hasDome(){
        if(tower.contains(Dome.class)){
            return true;
        }
        return false;
    }



    public void setWorkerOn(Worker worker) {
        this.workerOnSquare = worker;
    }

    public void addBlock() {
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




    @Override
    public String toString() {
        String ret = "";
        switch (getHeight()) {
            case 0:
                ret = "0";
                break;
            case 1:
                ret = "1";
                break;
            case 2:
                ret = "2";
                break;
            case 3:
                ret = "3";
                break;
            case 4:
                ret = "4";
                break;
        }

        if (workerOnSquare != null){
            ret += "X";
        }
        return ret;
    }


}


