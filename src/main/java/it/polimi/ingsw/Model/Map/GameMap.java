package it.polimi.ingsw.Model.Map;


import it.polimi.ingsw.Model.Game;
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
           for ( int j=0; j<COLUMNS; j++)  {
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

    /**
     * Gets worker on square.
     *
     * @param position the position
     * @return the worker on square or NULL if  !{@link Square#hasWorkerOn()}
     */
    public Worker getWorkerOnSquare(Position position) {
       return getSquare(position).getWorkerOnSquare();
    }

    /**
     * Gets square height.
     *
     * @param position the position we want to know the height
     * @return the square height
     */
    public int getSquareHeight(Position position) {
        return board[position.getRow()][position.getColumn()].getHeight();
    }

    //SHOULD NEVER BE USED -- ONLY TESTING
    public void setSquareHeight(Position position, int lvl) {
       board[position.getRow()][position.getColumn()].setHeight(lvl);
    }


    /**
     * Gets the difference in altitude between the squares in {@link Position from} and {@link Position to} inside the board
     *
     * @param from  starting position
     * @param to    arrival position
     * @return the difference in altitude
     */
    public int getDifferenceInAltitude(Position from, Position to) {
        return getBoard()[from.getRow()][from.getColumn()].getHeight() - getBoard()[to.getRow()][to.getColumn()].getHeight();
    }

    /**
     * Return the adjacent places with a difference in altitude less than 1 going up
     *
     * @param from where you are right now
     * @return the reachable adjacent places
     */
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

    /**
     * Return adjacent places where it is possible to build on
     *
     * @param whereToMove the square where you would like to move
     * @return the places where you can build on
     */
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


    /**
     * Build a block onto the given {@link Position position}
     *
     * @param position the position where we want to build on
     */
    public void addBlock(Position position) {
        if (getSquareHeight(position) >= 0 && getSquareHeight(position) <= 2) {
            //build a block
            getBoard()[position.getRow()][position.getColumn()].heightPlusOne();
        } else if (getSquareHeight(position) == 3 ) {
            //build a dome
            getBoard()[position.getRow()][position.getColumn()].heightPlusOne();
        } else {
            //
            System.out.println("There is already a Dome!");
        }
    }

    /**
     * Check if a {@link Position position} on the {@link GameMap gameMao} is real and free.
     *
     * @param position - the position we want to know if exists and free.
     *
     * @return boolean
     */
    private boolean isPositionValid(Position position){
       if (isPositionOnMapReal(position) && isPositionFree(position))
           return true;
       return false;
    }

    /**
     * Check if a {@link Position position} on the {@link GameMap gameMao} is real.
     *
     * @param position - the position we want to know if exists.
     *
     * @return boolean
     */
    private boolean isPositionOnMapReal(Position position){
       if ( position.getColumn() < 0 || position.getColumn() >= COLUMNS ||
            position.getRow() < 0 || position.getRow() >= ROWS)
           return false;
       return true;
   }

    /**
     * Check if a {@link Square square} in {@link Position position} has someone else's worker on.
     *
     * @param position - the position we want to know if it is free
     *
     * @return boolean
     */
   private boolean isPositionFree(Position position){
       if (board[position.getRow()][position.getColumn()].hasWorkerOn())
           return false;
       return true;
   }


    /**
     * Update the {@link Worker worker} position and the {@link GameMap} too;
     *
     * @param worker   the worker
     * @param position the position
     */
    public void setWorkerPosition(Worker worker, Position position){
       if ( isPositionValid(position)) {
           //Libero la posizione precedentemente occupata dal worker
           if ( worker.isPlaced() ) {
               this.getSquare(worker.getWorkerPosition()).freeSquare();
           }
           worker.setPosition(position);
           //devo liberare la vecchia posizione

           board[position.getRow()][position.getColumn()].setWorkerOn(worker);

       }
   }


    /**
     * Print board.
     */
    public void printBoard() {
       String string = "";
       for (int i=0; i<ROWS; i++) {
           for ( int j=0; j<COLUMNS; j++) {
               string = string.concat("|"+ board[i][j].toString() + "\t");
           }
           string = string.concat("|\n");

       }
        System.out.println(string);
    }

}
