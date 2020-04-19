package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    
    //Numero del worker
    private int workersNumber;

    private boolean isBlocked;
    private int height;
    private Position workerPosition;
    private boolean placed;


    //ArayList contenente le posizioni in cui il worker selezionato può muoversi
    private List<Position> adjacentPosition;

    public void hasMoved() { };
    public void hasBuilt() { };


    public Worker(int workersNumber) {
        this.height = 0;
        this.workerPosition = null;
        this.workersNumber = workersNumber;
        this.isBlocked = false;
        this.placed = false;
    }

    public Position getWorkerPosition() {
        return workerPosition;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    private Worker placeNewWorker(Position position) {
        this.setPosition(position);
        return this;
    }


    /**
     * It checks if the {@link Worker worker} has no reachable places or in case he has than if he has adjacent places to build on
     *
     *
     * @return boolean
     */
    public boolean isStuck() {
        ArrayList<Position> placesWhereToMove;
        placesWhereToMove = Game.getInstance().getGameMap().getReachableAdjacentPlaces(workerPosition);

        if (placesWhereToMove.size() == 0) return true;


        for (Position position: placesWhereToMove ) {
            ArrayList<Position> placesWhereYouCanBuildOn = Game.getInstance().getGameMap().getPlacesWhereYouCanBuildOn(position);
            if (placesWhereYouCanBuildOn.size() != 0) return false;
        }

        return true;
    }

    /**
     * Sets position check if the {@link Position newPosition} is free and if so it update the {@link Worker#workerPosition}
     * ps: branch else should never be reached because the {@link Position newPosition} will not be available to the player to chose
     *
     * @param newPosition will be the new Worker Position
     */
    public void setPosition(Position newPosition) {
        if (newPosition.isFree()) {

            System.out.println("Posizione libera, aggiorno la posizione del worker");
            this.workerPosition = newPosition;

            System.out.println("Imposto che su quel determinato Square ci sta un worker");
            Game.getInstance().getGameMap().getSquare(newPosition).setWorkerOn();
        } else {
            System.out.println("Posizione già occupata");
        }
    }



    @Override
    public String toString() {
        return "Worker " + workersNumber + "si trova in " + workerPosition;
    }

    /**
     * Set {@link Worker#placed} true.
     */
    public void isPlaced() {
        this.placed = true;
    }
}
