package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

import java.util.List;

public class Worker {

    //Quanti worker ha il giocatore
    private static int numberOfWorkers = 1;

    //Numero del worker
    private int workersNumber;

    private boolean isBlocked;
    private int height;
    private Position workerPosition;


    //ArayList contenente le posizioni in cui il worker selezionato può muoversi
    private List<Position> adjacentPosition;

    public void hasMoved() { };

    public void hasBuilt() { };


    public Worker() {
        this.height = 0;
        this.workerPosition = null;
        this.workersNumber = numberOfWorkers;
        this.isBlocked = false;
        numberOfWorkersPlusOne();
    }

    //Metodo per tener conto di quanti worker ho in gioco
    private static void numberOfWorkersPlusOne() {
        Worker.numberOfWorkers++;
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
