package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Player.Position;

public class Square {

    private int x;
    private int y;

    private int height;

    private boolean workerOn;
    private boolean builtOver;


    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
    }


    public int getHeight() {
        return this.height;
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

    @Override
    public String toString() {
        return "position " + this.x + "," + this.y +
                "; Height: " + this.height;
    }
}
