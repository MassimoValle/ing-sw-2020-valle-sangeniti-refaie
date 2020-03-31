package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

public class Worker {

    private int height;
    private Position workerPosition;

    public void hasMoved() { };

    public void hasBuilt() { };


    public Worker() {
        this.height = 0;
        this.workerPosition = null;
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
}
