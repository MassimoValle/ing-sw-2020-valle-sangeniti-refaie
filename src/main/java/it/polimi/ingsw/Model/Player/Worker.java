package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

import java.util.List;

public class Worker {

    //Quanti worker ha il giocatore
    private static int numberOfWorkers = 1;

    //Numero del worker
    private int workersNumber;


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
        numberOfWorkersPlusOne();
    }

    //Metodo per tener conto di quanti worker ho in gioco
    private static void numberOfWorkersPlusOne() {
        Worker.numberOfWorkers++;
    }

    private Worker placeNewWorker(Worker newWorker, Position position) {
        newWorker.setWorkerPosition(position);
        //[position.getColumn()][position.getRaw()]
        //this.height = position;
        return newWorker;
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
        System.out.println("Worker ");

        return null;
    }
}
