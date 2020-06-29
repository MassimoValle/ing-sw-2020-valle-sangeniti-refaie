package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.exceptions.PositionOutsideBoardException;
import it.polimi.ingsw.server.model.Map.GameMap;

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

    public boolean insideBoard() {
        return this.row >= 0 && this.row <= 4 && this.column >= 0 && this.column <= 4;
    }

    /**
     * This method check if the 2 position are at unit distance;.
     *
     * @param pos the pos 2
     * @return the boolean
     */
    public boolean isClose(Position pos) {
        return this.row - pos.row >= -1 && this.row - pos.row <= 1 && this.column - pos.column >= -1 && this.column - pos.column <= 1;
    }

    public boolean sameRow(Position pos) {
        return this.row == pos.row;
    }

    public boolean sameColumn(Position pos) {
        return this.column == pos.column;
    }

    public boolean isInCorner() {
        return this.equals(new Position(0, 0)) || this.equals(new Position(0, 4)) ||
                this.equals(new Position(4, 0)) || this.equals(new Position(4, 4));

    }

    public Position getBackwardPosition(Position pos2) throws PositionOutsideBoardException {

        Position pos1 = this;

        int x1 = this.row;
        int x2 = pos2.getRow();
        int y1 = this.column;
        int y2 = pos2.getColumn();
        int x3,y3;


        if ((pos2.isPerimetral() && pos1.isClose(pos2) && !pos1.isPerimetral() ) || pos2.isInCorner() || !pos1.isClose(pos2))
            throw new PositionOutsideBoardException();


        if (pos1.sameRow(pos2)) {
            x3 = x2;
            if (y1 < y2) {
                y3 = y2 + 1;
                return new Position(x3, y3);
            } else {
                y3 = y2 - 1;
            }
            return new Position(x3, y3);
        }

        if (pos1.sameColumn(pos2)) {
            y3 = y2;
            if (x1 < x2) {
                x3 = x2 + 1;
                return new Position(x3, y3);
            } else {
                x3 = x2 - 1;
            }
            return new Position(x3, y3);
        }

        if (x1 < x2) {
            if (y1 < y2) {
                x3 = x2 + 1;
                y3 = y2 + 1;
                return new Position(x3,y3);
            }else if (y1 == y2){
                x3 = x2 + 1;
                y3 = y2;
                return new Position(x3,y3);
            }else {
                x3 = x2 + 1;
                y3 = y2 - 1;
                return new Position(x3,y3);
            }
        }else {
            if(y1 < y2){
                x3 = x2 - 1;
                y3 = y2 + 1;
                return new Position(x3,y3);
            }else if (y1 == y2){
                x3 = x2 -1;
                y3 = y2;
                return new Position(x3,y3);
            }else {
                x3 = x2 - 1;
                y3 = y2 - 1;
                return new Position(x3,y3);
            }
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).getColumn() == this.getColumn() && ((Position) obj).getRow() == this.getRow();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getColumn();
        return result;
    }

    @Override
    public String toString() {
        return "( " + this.getRow() + " , " + this.getColumn() + " )";
        }



}

