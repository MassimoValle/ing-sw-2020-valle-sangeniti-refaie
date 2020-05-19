package it.polimi.ingsw.Server.Model.Player;

import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;

import java.io.Serializable;
import java.util.ArrayList;

//Posizione relativa alla board

public class Position implements Serializable {

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
     * This check the {@link GameMap gameMap } boundaries and
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

    public boolean isPerimetral() {
        return row == 0 || row == 4 || column == 0 || column == 4;
    }


    /**
     * Check if a {@link Square square} has someone else's worker on.
     *
     * @return boolean
     */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).getColumn() == this.getColumn() && ((Position) obj).getRow() == this.getRow();
        }
        return false;
    }

    @Override
    public String toString() {
        return "( " + this.getRow() + " , " + this.getColumn() + " )";
        }


    }

