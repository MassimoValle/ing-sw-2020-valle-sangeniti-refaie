package it.polimi.ingsw.server.model.Map;

import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.server.model.Player.Worker;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    protected final int COLUMNS = 5;
    protected final int ROWS = 5;

    protected Square[][] board;





   public GameMap() {
       this.board = new Square[COLUMNS][ROWS];

       for (int i=0; i<ROWS; i++) {
           for ( int j=0; j<COLUMNS; j++)  {
               this.board[i][j] = new Square(i,j);
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

    //per test
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
    public List<Position> getReachableAdjacentPlaces(Position from) {
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
     * It check if there's at least one reachable adjacent square -1 height compared to the square given
     *
     * @param square square
     * @return true if there's at least one reachable adjacent square -1 height compared to the square given, false otherwise
     */
    public boolean squareMinusOneAvailable(Square square) {

        for (Position pos : this.getReachableAdjacentPlaces(square.getPosition())) {
            if (square.getHeight() - this.getSquare(pos).getHeight() >= 1)
                return true;
        }

        return false;
    }


    /**
     * It check if there's at least one reachable adjacent square with the same height compared to the square given
     *
     * @param square square
     * @return true if there's at least one reachable adjacent square with the same height compared to the square given, false otherwise
     */
    public boolean squareSameHeightAvailable(Square square) {

        for (Position pos : this.getReachableAdjacentPlaces(square.getPosition())) {
            if (square.getHeight() == this.getSquare(pos).getHeight())
                return true;
        }

        return false;
    }

    /**
     * It check if there are at least two reachable adjacent squares with the same height
     *
     * @param square square
     * @return true if there are at least two reachable adjacent squares with the same height, false otherwise
     */
    public boolean twoSquaresSameHeightAvailable(Square square) {
        ArrayList<Position> positions = new ArrayList<>();

        for (Position pos : this.getReachableAdjacentPlaces(square.getPosition())) {
            if (square.getHeight() == this.getSquare(pos).getHeight())
                positions.add(pos);
        }
        return positions.size() >= 2;
    }

    /**
     * TODO DA TROVARE UN NOME DECENTE
     * It check if there are at least 2 buildable squares and 1 square with the same height
     *
     * @param square the square
     * @return true if there are at least 2 buildable squares and 1 square with the same height, false otherwise
     */
    public boolean prometheusBuildsFirst(Square square) {
        ArrayList<Position> buildableSquares = this.getPlacesWhereYouCanBuildOn(square.getPosition());

        return buildableSquares.size() > 1 && this.squareSameHeightAvailable(square);
    }

    /**
     * Print board.
     */
    public void printBoard() {
       String string = "";
        string = string.concat("\t0"+"       1"+"       2"+"       3"+"       4");
        string = string.concat("\n_________________________________________\n");
        for (int i=0; i<ROWS; i++) {
            for ( int j=0; j<COLUMNS; j++) {
                string = string.concat("|"+ board[i][j].toString() + "\t");
            }
            string = string.concat("|" + "  " + i +"\n");
           string = string.concat("_________________________________________\n");
       }
        System.out.println(string);
    }

    @Override
    public String toString() {
        printBoard();
        return "";
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

    public boolean hasAtLeastFiveFullTower() {

        int fullSquares = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j].isFull())
                    fullSquares++;

            }
        }
        return fullSquares >= 5;
    }

    /**
     * Remove {@link Worker Player's wokers} from the board
     *
     * @param playerToRemove the player to remove
     */
    public void removePlayerWorkers(Player playerToRemove) {
        for (Worker worker : playerToRemove.getPlayerWorkers()) {
            this.getSquare(worker.getPosition()).freeSquare();
        }
    }

    /**
     * It checks if starting from the position given you can only move up
     *
     * @param reachables       {@link List<Position>} MUST NOT BE EMPTY
     * @param startingPosition the starting {@link Position}
     *
     * @return true if the worker can only move up, false otherwise;
     */
    public boolean forcedToMoveUp( List<Position> reachables, Position startingPosition) {

        for (Position pos : reachables) {
            if ( getSquare(pos).getHeight() <= getSquare(startingPosition).getHeight() )
                return false;
        }

        return true;
    }
}
