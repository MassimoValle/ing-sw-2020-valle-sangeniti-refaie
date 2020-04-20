package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Player.Worker;

public class Square {

    private int row;
    private int column;

    //To keep trace of whose worker is on the square
    private Worker workerOnSquare;


    private int height;

    private boolean workerOn;
    private boolean builtOver;


    public Square(int row, int column) {
        this.row = row;
        this.column = column;
        this.height = 0;
        this.workerOnSquare = null;
    }


    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean hasWorkerOn() {
        return workerOn;
    }

    public boolean hasBeenBuiltOver() {
        return builtOver;
    }

    public void heightPlusOne() {
        this.height++;
    }

    public void setWorkerOn(Worker worker) {
        this.workerOn = true;
        this.workerOnSquare = worker;
    }

    @Override
    public String toString() {
        switch (getHeight()) {
            case 0:
                if (workerOn) {
                    return "0X";
                } else return "0";
            case 1:
                if (workerOn) {
                    return "1X";
                } else return "1";
            case 2:
                if (workerOn) {
                    return "2X";
                } else return "2";
            case 3:
                if (workerOn) {
                    return "3X";
                } else return "3";
            case 4:
                if (workerOn) {
                    return "DX";
                } else return "D";
        }
        return null;
    }
}


