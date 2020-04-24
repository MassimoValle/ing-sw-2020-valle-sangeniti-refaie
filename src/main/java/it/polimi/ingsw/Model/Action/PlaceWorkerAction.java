package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class PlaceWorkerAction implements Action{

    private Player workersOwner;
    private Worker workerToBePlaced;
    private Position positionWhereToPlace;
    private Square square;

    public PlaceWorkerAction(Player workersOwner, Worker workerToBePlaced, Position positionWhereToPlace, Square square) {
        this.workersOwner = workersOwner;
        this.workerToBePlaced = workerToBePlaced;
        this.positionWhereToPlace = positionWhereToPlace;
        this.square = square;

    }


    @Override
    public boolean isValid() {
        //Devo controllare che quel worker non è gia piazzato è che nella posizione indicata dal player non ci sia già un altro worker
        if (workerToBePlaced.isPlaced()) {
            return false;
        } else if (square.hasWorkerOn()) {
            return false;
        }
        return true;
    }

    @Override
    public void doAction() {
        //Aggiorno la posizione del worker
        workerToBePlaced.setPosition(positionWhereToPlace);
        //Aggiorno la mappa di gioco
        square.setWorkerOn(workerToBePlaced);
        //Aggiorno il fatto che il worker adesso risulta piazzato
        workerToBePlaced.setPlaced(true);
    }
}
