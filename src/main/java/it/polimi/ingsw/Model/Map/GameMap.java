package it.polimi.ingsw.Model.Map;


import it.polimi.ingsw.Model.Player.Position;

import java.util.ArrayList;

public class GameMap {

    public final int COLUMNS = 5;
    public final int RAWS = 5;

    private Square[][] board;

   public GameMap() {
       this.board = buildBoard();
       for (int i=0; i<COLUMNS; i++) {
           for ( int j=0; j<RAWS; j++) {
               this.board[i][j] = new Square(i, j);
           }
       }
   }

    public Square[][] getBoard() {
        return this.board;
    }

    private Square[][] buildBoard() {
       return new Square[COLUMNS][RAWS];
    }

    //METODI DA ELIMINARE
    public int getSquareHeight(Position position) {
        return board[position.getColumn()][position.getRaw()].getHeight();
    }
    public void setSquareHeight(Position position) {
        board[position.getColumn()][position.getRaw()].heightPlusOne();
    }



    //Returns the difference in altitude between two position inside the board
    public int getDifferenceInAltitude(Position from, Position to) {
        return getBoard()[from.getRaw()][from.getColumn()].getHeight() - getBoard()[to.getRaw()][to.getColumn()].getHeight();
    }

    //Return the adjacent places with a difference in altitude less than 1 going up
    public ArrayList<Position> getReachableAdjacentPlaces(Position from) {
       ArrayList<Position> adjacentPlaces = from.getAdjacentPlaces();

       ArrayList<Position> reachablePlaces = new ArrayList<>();

       for(Position pos : adjacentPlaces) {
           if(getDifferenceInAltitude(from, pos) >= -1 && !( getBoard()[pos.getRaw()][pos.getColumn()].hasWorkerOn() ) ) {
               reachablePlaces.add(pos);
           }
       }

       return reachablePlaces;
    }

    //Return adjacent places whereit is possible to build on
    public ArrayList<Position> getPlacesWhereYouCanBuildOn(Position whereToMove) {
        ArrayList<Position> whereToMoveAdjacentPlaces = whereToMove.getAdjacentPlaces();

        //ArrayList that contains the positions that are at level 3 or lower && no worker on
        ArrayList<Position> placesWhereYouCanBuildOn = new ArrayList<>();

        for (Position pos : whereToMoveAdjacentPlaces) {
            if (getSquareHeight(pos) < 4 && getSquareHeight(pos) >= 0 && !(getBoard()[pos.getRaw()][pos.getColumn()].hasWorkerOn())) {
                placesWhereYouCanBuildOn.add(pos);
            }
        }
        return placesWhereYouCanBuildOn;
    }




    public void addBlock(Position position) {
        if (getSquareHeight(position) < 4 ) {
            setSquareHeight(position);
        } else {
            //TODO: GESTIRE CON ECCEZZIONE!!!
            System.out.println("There is already a Dome!");
        }
    }

    //TODO
    public void squareHeightPlusOne(Square square) {
       square.heightPlusOne();
    }

    //TODO
    @Override
    public String toString() {
       for (int i=0; i<RAWS; i++) {
           for ( int j=0; j<COLUMNS; j++) {
               System.out.println("Square" + i + " , " + j + " Lvl :" + board[i][j].getHeight());
           }
       }
        return null;
    }

}
