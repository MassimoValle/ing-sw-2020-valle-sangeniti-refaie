package it.polimi.ingsw.Model.Map;


import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

import java.util.ArrayList;

public class GameMap {

    public final int COLUMNS = 5;
    public final int ROWS = 5;

    private Square[][] board;




   public GameMap() {
       this.board = new Square[COLUMNS][ROWS];

       for (int i=0; i<ROWS; i++) {
           for ( int j=0; j<COLUMNS; j++)  {
               this.board[i][j] = new Square();
           }
       }
   }




    public Square[][] getBoard() {
        return this.board;
    }

    /**
     * It gets the {@link Square} defined by that {@link Position}
     *
     * @param pos the square position
     * @return the square
     */
    public Square getSquare(Position pos) {
       return getBoard()[pos.getRow()][pos.getColumn()];
    }

    public Square getSquare(int row, int col) {
        return getBoard()[row][col];
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

    public Worker getWorkerOnSquare(int row, int col) {
        return getSquare(row, col).getWorkerOnSquare();
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
           if(getDifferenceInAltitude(from, pos) >= -1 && !(getBoard()[pos.getRow()][pos.getColumn()].hasWorkerOn()) &&
                   !getBoard()[pos.getRow()][pos.getColumn()].hasDome() ) {
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
            if (getSquareHeight(pos) < 4 && getSquareHeight(pos) >= 0 && !(getBoard()[pos.getRow()][pos.getColumn()].hasWorkerOn()) &&
                    !getBoard()[pos.getRow()][pos.getColumn()].hasDome()) {
                placesWhereYouCanBuildOn.add(pos);
            }
        }
        return placesWhereYouCanBuildOn;
    }

    /**
     * Check if a {@link Position position} on the {@link GameMap gameMao} is real and free.
     *
     * @param position - the position we want to know if exists and free.
     *
     * @return boolean
     */
    public boolean isPositionValid(Position position){
        return isPositionOnMapReal(position) && isPositionFree(position);
    }


    /**
     * It checks if the {@link Worker worker} has no reachable places or in case he has than if he has adjacent places to build on
     *
     *
     * @return boolean
     */
    public boolean isWorkerStuck(Worker worker) {
        ArrayList<Position> placesWhereToMove;
        placesWhereToMove = getReachableAdjacentPlaces(worker.getWorkerPosition());

        if (placesWhereToMove.size() == 0) return true;


        for (Position position: placesWhereToMove ) {
            ArrayList<Position> placesWhereYouCanBuildOn = getPlacesWhereYouCanBuildOn(position);
            if (placesWhereYouCanBuildOn.size() != 0) return false;
        }

        return true;
    }



    /**
     * Print board.
     */
    public void printBoard() {
       String string = "";
       string = string.concat("_________________________________________\n");
       for (int i=0; i<ROWS; i++) {
           for ( int j=0; j<COLUMNS; j++) {
               string = string.concat("|"+ board[i][j].toString() + "\t");
           }
           string = string.concat("|\n");
           string = string.concat("_________________________________________\n");
       }
        System.out.println(string);
    }

    @Override
    public String toString() {
        printBoard();
        return null;
    }



    // ### helper ###

    /**
     * Check if a {@link Position position} on the {@link GameMap gameMao} is real.
     *
     * @param position - the position we want to know if exists.
     *
     * @return boolean
     */
    public boolean isPositionOnMapReal(Position position){
        return position.getColumn() >= 0 && position.getColumn() < COLUMNS &&
                position.getRow() >= 0 && position.getRow() < ROWS;
    }

    /**
     * Check if a {@link Square square} in {@link Position position} has a worker on.
     *
     * @param position - the position we want to know if it is free
     *
     * @return boolean
     */
    public boolean isPositionFree(Position position){
        return !board[position.getRow()][position.getColumn()].hasWorkerOn();
    }

}
