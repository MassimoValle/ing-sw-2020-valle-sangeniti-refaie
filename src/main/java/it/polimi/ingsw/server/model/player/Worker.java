package it.polimi.ingsw.server.model.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class Worker {

    private final int workersNumber;
    private final Player owner;
    private ColorEnum color;

    private Position workerPosition;
    private boolean placed;

    private boolean selected;


    //GUI
    private ImageView imgView;


    //ArayList contenente le posizioni in cui il worker selezionato può muoversi
    private List<Position> adjacentPosition;


    public Worker(Player owner, int workersNumber) {
        this.workerPosition = null;
        this.workersNumber = workersNumber;
        this.placed = false;
        this.selected = false;
        this.owner = owner;
    }

    public void initGUIObj(){
        this.imgView = new ImageView(this.setImg());
    }

    public void selectedOnGUI(){

        String path = "/imgs/workers/";

        switch (getOwner().getColor()){
            case RED -> path += "BrownWorkerGLOW.png";
            case BLUE -> path += "BlueWorkerGLOW.png";
            case GREEN -> path += "WhiteWorkerGLOW.png";
        }

        this.imgView.setImage(new Image(path));
    }

    public void deselectedOnGUI(){
        imgView.setImage(setImg());
    }



    private Image setImg() {
        String path = "/imgs/workers/";
        switch (getOwner().getColor()){
            case RED -> path += "BrownWorker.png";
            case BLUE -> path += "BlueWorker.png";
            case GREEN -> path += "WhiteWorker.png";
        }

        return new Image(path);
    }

    public ImageView getImgView() {
        return imgView;
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

    @Override
    public int hashCode() {
        int result = getOwner() != null ? getOwner().hashCode() : 0;
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        return result;
    }
}
