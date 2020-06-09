package it.polimi.ingsw.Server.Model.Player;

import java.util.List;

public class Worker {

    private int workersNumber;
    private final Player owner;
    private ColorEnum color;

    private Position workerPosition;
    private boolean placed;

    private boolean selected;


    //ArayList contenente le posizioni in cui il worker selezionato può muoversi
    private List<Position> adjacentPosition;


    public Worker(Player owner, int workersNumber) {
        this.workerPosition = null;
        this.workersNumber = workersNumber;
        this.placed = false;
        this.selected = false;
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public Position getPosition() {
        return workerPosition;
    }

    public int getNumber() {
        return workersNumber;
    }

    public ColorEnum getColor() {
        return color;
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

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    /**
     * Update the {@link Worker#workerPosition}
     *
     * @param position will be the new Worker Position
     */
    public void setPosition(Position position) {
        this.workerPosition = position;

    }


    @Override
    public String toString() {
        return "\nWorker " + (workersNumber+1)  + " si trova in " + workerPosition;
    }

    public void remove() {
        this.setPosition(null);
        this.placed = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Worker)) {
            return false;
        }

        Worker worker = (Worker) obj;

        return owner.equals(worker.owner) &&
                workersNumber == worker.workersNumber &&
                placed == worker.placed &&
                selected == worker.selected &&
                workerPosition.equals(worker.workerPosition);


    }
}
