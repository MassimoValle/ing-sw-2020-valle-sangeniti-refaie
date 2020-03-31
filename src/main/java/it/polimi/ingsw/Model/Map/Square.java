package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Player.Position;

public class Square {

    private int height;

    private boolean workerOn;
    private boolean builtOver;

    public Square() {
        this.height = 0;
    }


    public int getHeight() {
        return this.height;
    }

    public boolean hasWorkerOn(Position position) {
        return builtOver;
    }

    public boolean hasBeenBuiltOver(Position position) {
        return workerOn;
    }

    public void heightPlusOne() {
        this.height++;
    }
}
