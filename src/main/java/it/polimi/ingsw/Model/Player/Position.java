package it.polimi.ingsw.Model.Player;

import java.util.List;

//Posizione relativa alla board
public class Position {

    private int column;
    private int raw;

    public Position(int column, int raw) {
        this.column = column;
        this.raw = raw;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRaw(int raw) {
        this.raw = raw;
    }

    public int getColumn(){
        return this.column;
    }

    public int getRaw() {
        return this.raw;
    }

    //Method to check if a square is taken by another player's worker
    boolean isFree() {
        //TODO
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).getColumn() == this.getColumn() && ((Position) obj).getRaw() == this.getRaw();
        }
        return false;
    }

}
