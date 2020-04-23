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

    private boolean selected;


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
        this.selected = false;
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

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Sets position check if the {@link Position newPosition} is free and if so it update the {@link Worker#workerPosition}
     * ps: branch else should never be reached because the {@link Position newPosition} will not be available to the player to chose
     *
     * @param position will be the new Worker Position
     */
    public void setPosition(Position position) {
        this.workerPosition = position;

    }


    @Override
    public String toString() {
        return "Worker " + workersNumber + "si trova in " + workerPosition;
    }

    /**
     * Set {@link Worker#placed} true.
     */
    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean isPlaced() {
        return placed;
    }

}
