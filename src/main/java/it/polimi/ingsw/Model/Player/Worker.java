package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

import java.util.List;

public class Worker {
    
    //Numero del worker
    private int workersNumber;

    private boolean isBlocked;
    private int height;
    private Position workerPosition;


    //ArayList contenente le posizioni in cui il worker selezionato può muoversi
    private List<Position> adjacentPosition;

    public void hasMoved() { };

    public void hasBuilt() { };


    public Worker(int workersNumber) {
        this.height = 0;
        this.workerPosition = null;
        this.workersNumber = workersNumber;
        this.isBlocked = false;
    }


    private Worker placeNewWorker(Position position) {
        this.setWorkerPosition(position);
        //[position.getColumn()][position.getRaw()]
        //this.height = position;
        return this;
    }

    public void setWorkerPosition(Position newPosition) {
        if (newPosition.isFree()) {
            this.workerPosition = newPosition;
        }
    }

    public Position getWorkerPosition() {
        return workerPosition;
    }

    public int getWorkersNumber() {
        return workersNumber;
    }

    @Override
    public String toString() {
        return "Worker " + workersNumber + "si trova in " + workerPosition;
    }
}
