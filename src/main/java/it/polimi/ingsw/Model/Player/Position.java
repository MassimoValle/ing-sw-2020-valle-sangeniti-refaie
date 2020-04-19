package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

import java.util.ArrayList;

//Posizione relativa alla board

public class Position {

    private int row;
    private int column;

    //CONSTRUCTOR
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    //SETTER & GETTER
    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn(){
        return this.column;
    }

    public int getRow() {
        return this.row;
    }




    // METHODS TO GET ADJACENT SQUARES

    /**
     * This check the {@link it.polimi.ingsw.Model.Map.GameMap gameMap } boundaries and
     * return the position between [0,0] and [4,4]
     *
     * @return the adjacent places
     */
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
        if(position.getRow() == 0) {
            return null;
        } else {
            return new Position(position.getRow() - 1, position.getColumn());
        }
    }

    private Position getNorthEast(Position position) {
        if (position.getRow() == 0 || position.getColumn() == 4 ) {
            return null;
        } else {
            return new Position(position.getRow() - 1 , position.getColumn() + 1);
        }
    }

    private Position getEast(Position position) {
        if (position.getColumn() == 4 ) {
            return null;
        } else {
            return new Position(position.getRow(), position.getColumn() +1);
        }
    }

    private Position getSouthEast(Position position) {
        if (position.getColumn() == 4 || position.getRow() == 4 ) {
            return null;
        } else {
            return new Position(position.getRow() + 1, position.getColumn() + 1);
        }
    }

    private Position getSouth(Position position) {
        if (position.getRow() == 4) {
            return null;
        } else return new Position(position.getRow() + 1, position.getColumn());
    }

    private Position getSouthWest(Position position) {
        if (position.getRow() == 4 || position.getColumn() == 0) {
            return null;
        } else {
            return new Position(position.getRow() + 1, position.getColumn() - 1);
        }
    }

    private Position getWest(Position position) {
        if (position.getColumn() == 0) {
            return null;
        } else {
            return new Position(position.getRow(), position.getColumn() - 1);
        }
    }

    private Position getNorthWest(Position position) {
        if (position.getRow() == 0 || position.getColumn() == 0) {
            return null;
        } else {
            return new Position(position.getRow() - 1, position.getColumn() - 1 );
        }
    }


    /**
     * Check if a {@link it.polimi.ingsw.Model.Map.Square square} has someone else's worker on.
     *
     * @return boolean
     */
//
    boolean isFree() {
        if ( Game.getInstance().getGameMap().getBoard()[this.row][this.column].hasWorkerOn() ) {
            return false;
        } else return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).getColumn() == this.getColumn() && ((Position) obj).getRow() == this.getRow();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Position: " + this.getRow() + " , " + this.getColumn();
        }

}
