package it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

//Posizione relativa alla board

public class Position {

    private int raw;
    private int column;

    //CONSTRUCTOR
    public Position(int raw, int column) {
        this.raw = raw;
        this.column = column;
    }

    //SETTER & GETTER
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




    // METHODS TO GET ADJACENT SQUARES

    public ArrayList<Position> getAdjacentPlaces() {
        ArrayList<Position> adjacentPlaces = new ArrayList<>();

        if (getNorth(this) != null ) adjacentPlaces.add(getNorth(this));
        if (getNorthEast(this) != null ) adjacentPlaces.add(getNorthEast(this));
        if (getEast(this) != null ) adjacentPlaces.add(getEast(this));
        if (getSouthEast(this) != null ) adjacentPlaces.add(getSouthEast(this));
        if (getSouth(this) != null ) adjacentPlaces.add(getSouth(this));
        if (getSouthWest(this) != null ) adjacentPlaces.add(getSouthWest(this));
        if (getWest(this) != null ) adjacentPlaces.add(getWest(this));
        if (getNorthWest(this) != null ) adjacentPlaces.add(getNorthWest(this));

        return adjacentPlaces;
    }

    private Position getNorth(Position position) {
        if(position.getRaw() == 0) {
            return null;
        } else {
            return new Position(position.getRaw() - 1, position.getColumn());
        }
    }

    private Position getNorthEast(Position position) {
        if (position.getRaw() == 0 || position.getColumn() == 4 ) {
            return null;
        } else {
            return new Position(position.getRaw() - 1 , position.getColumn() + 1);
        }
    }

    private Position getEast(Position position) {
        if (position.getColumn() == 4 ) {
            return null;
        } else {
            return new Position(position.getRaw(), position.getColumn() +1);
        }
    }

    private Position getSouthEast(Position position) {
        if (position.getColumn() == 4 || position.getRaw() == 4 ) {
            return null;
        } else {
            return new Position(position.getRaw() + 1, position.getColumn() + 1);
        }
    }

    private Position getSouth(Position position) {
        if (position.getRaw() == 4) {
            return null;
        } else return new Position(position.getRaw() + 1, position.getColumn());
    }

    private Position getSouthWest(Position position) {
        if (position.getRaw() == 4 || position.getColumn() == 0) {
            return null;
        } else {
            return new Position(position.getRaw() + 1, position.getColumn() - 1);
        }
    }

    private Position getWest(Position position) {
        if (position.getColumn() == 0) {
            return null;
        } else {
            return new Position(position.getRaw(), position.getColumn() - 1);
        }
    }

    private Position getNorthWest(Position position) {
        if (position.getRaw() == 0 || position.getColumn() == 0) {
            return null;
        } else {
            return new Position(position.getRaw() - 1, position.getColumn() - 1 );
        }
    }


    //Check if a square is taken by another player's worker
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

    @Override
    public String toString() {
        return "Position: " + this.getRaw() + " , " + this.getColumn();
        }

}
