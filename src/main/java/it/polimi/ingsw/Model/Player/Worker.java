package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Worker {
    
    //Numero del worker
    private int workersNumber;


    private Color color;

    private boolean isBlocked;
    private int height;
    private Position workerPosition;
    private boolean placed;

    private boolean selected;


    //ArayList contenente le posizioni in cui il worker selezionato può muoversi
    private List<Position> adjacentPosition;


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

    public Color getColor() {
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

    public void setColor(Color color) {
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
        return "\nWorker " + (workersNumber+1)  + "si trova in " + workerPosition;
    }

}
