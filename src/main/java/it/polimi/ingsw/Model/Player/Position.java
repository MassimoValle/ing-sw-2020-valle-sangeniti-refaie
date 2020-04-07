package it.polimi.ingsw.Model.Player;

import java.util.ArrayList;
import java.util.List;

//Posizione relativa alla board

public class Position {

    private int raw;
    private int column;


    public Position(int raw, int column) {
        this.raw = raw;
        this.column = column;
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

    // METHODS TO GET ADJACENT SQUARES
    //TODO da modificare il tipo di ritorno per gestire la dimensione dell'array
    // altrimenti è sempre 8 anche se avrò solo 2 posizioni disponibili
    public ArrayList<Position> getAdjacentPlaces(Position position) {
        ArrayList<Position> adjacentPlaces = new ArrayList<>();
        adjacentPlaces.add(getNorth(position));
        adjacentPlaces.add(getNorthEast(position));
        adjacentPlaces.add(getEast(position));
        adjacentPlaces.add(getSouthEast(position));
        adjacentPlaces.add(getSouth(position));
        adjacentPlaces.add(getSouthWest(position));
        adjacentPlaces.add(getWest(position));
        adjacentPlaces.add(getNorthWest(position));
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

    @Override
    public String toString() {
        System.out.println("Position: " + this.getRaw() + " , " + this.getColumn());
        return null;
        }

}
