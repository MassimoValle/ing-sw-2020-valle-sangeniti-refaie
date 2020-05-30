package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

/**
 * The Place worker action lets you place the worker on the board in the square you want if it is free
 */
public class PlaceWorkerAction implements Action{

    private Worker workerToBePlaced;
    private Position positionWhereToPlace;
    private Square squareWhereToPlace;

    public PlaceWorkerAction(Worker workerToBePlaced, Position positionWhereToPlace, Square squareWhereToPlace) {
        this.workerToBePlaced = workerToBePlaced;
        this.positionWhereToPlace = positionWhereToPlace;
        this.squareWhereToPlace = squareWhereToPlace;
    }


    @Override
    public boolean isValid() {
        //Devo controllare che quel worker non è gia piazzato è che nella posizione indicata dal player non ci sia già un altro worker
        if (workerToBePlaced.isPlaced()) {
            return false;
        } else if (squareWhereToPlace.hasWorkerOn()) {
            return false;
        }
        return true;
    }

    @Override
    public void doAction() {
        //Aggiorno la posizione del worker
        workerToBePlaced.setPosition(positionWhereToPlace);
        //Aggiorno la mappa di gioco
        squareWhereToPlace.setWorkerOn(workerToBePlaced);
        //Aggiorno il fatto che il worker adesso risulta piazzato
        workerToBePlaced.setPlaced(true);
    }
}
