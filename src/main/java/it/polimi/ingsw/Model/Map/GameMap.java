package it.polimi.ingsw.Model.Map;


import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

import java.util.ArrayList;

public class GameMap {

    public final int COLUMNS = 5;
    public final int ROWS = 5;

    private Square[][] board;

   public GameMap() {
       this.board = buildBoard();
       for (int i=0; i<ROWS; i++) {
           for ( int j=0; j<COLUMNS; j++) {
               this.board[i][j] = new Square(i, j);
           }
       }
   }

    public Square[][] getBoard() {
        return this.board;
    }

    public Square getSquare(Position pos) {
       return getBoard()[pos.getRow()][pos.getColumn()];
    }

    private Square[][] buildBoard() {
       return new Square[COLUMNS][ROWS];
    }

    //METODI DA ELIMINARE
    public int getSquareHeight(Position position) {
        return board[position.getRow()][position.getColumn()].getHeight();
    }
    public void setSquareHeight(Position position) {
        board[position.getRow()][position.getColumn()].heightPlusOne();
    }



    //Returns the difference in altitude between two position inside the board
    public int getDifferenceInAltitude(Position from, Position to) {
        return getBoard()[from.getRow()][from.getColumn()].getHeight() - getBoard()[to.getRow()][to.getColumn()].getHeight();
    }

    //Return the adjacent places with a difference in altitude less than 1 going up
    public ArrayList<Position> getReachableAdjacentPlaces(Position from) {
       ArrayList<Position> adjacentPlaces = from.getAdjacentPlaces();

       ArrayList<Position> reachablePlaces = new ArrayList<>();

       for(Position pos : adjacentPlaces) {
           if(getDifferenceInAltitude(from, pos) >= -1 && !( getBoard()[pos.getRow()][pos.getColumn()].hasWorkerOn() ) ) {
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
            if (getSquareHeight(pos) < 4 && getSquareHeight(pos) >= 0 && !(getBoard()[pos.getRow()][pos.getColumn()].hasWorkerOn())) {
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

    private boolean isPositionValid(Position position){
       if (isPositionOnMapReal(position) && isPositionFree(position))
           return true;
       return false;
    }

    private boolean isPositionOnMapReal(Position position){  //verifica se la posizione è valida sulla mappa
       if ( position.getColumn() < 0 || position.getColumn() >= COLUMNS ||
            position.getRow() < 0 || position.getRow() >= ROWS)
           return false;
       return true;
   }

   private boolean isPositionFree(Position position){
       if (board[position.getRow()][position.getColumn()].hasWorkerOn())
           return false;
       return true;
   }

   public void setWorkerPosition(Worker worker, Position position){
       if ( isPositionValid(position)) {
           worker.setPosition(position);
           worker.setPlaced();
           board[position.getRow()][position.getColumn()].setWorkerOn();

       }
   }




    public String printBoard() {
       String string = "";
       for (int i=0; i<ROWS; i++) {
           for ( int j=0; j<COLUMNS; j++) {
               string = string.concat("|"+ board[i][j].toString() + "\t");
           }
           string = string.concat("|\n");

       }
        return string;
    }

}
