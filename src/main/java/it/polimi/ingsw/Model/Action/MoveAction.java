package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

/**
 * The MoveAction update the workers position & the gameMap
 */
public class MoveAction implements Action {

    private final Worker playerWorker;
    private final Position newPosition;
    private Square oldPositionSquare;
    private Square newPositionSquare;


    public MoveAction(Worker playerWorker, Position newPosition,Square oldPositionSquare, Square newPositionSquare) {
        this.playerWorker = playerWorker;
        this.newPosition = newPosition;
        this.oldPositionSquare = oldPositionSquare;
        this.newPositionSquare = newPositionSquare;
    }

    @Override
    public boolean isValid() {
        if (newPositionSquare.hasWorkerOn()) {
            return false;
        }
        return true;
    }



    /**
     * Move the selected {@link Worker playerWorker}  by {@link Player player} into the {@link Position newPosition} chose by the player;
     * Also update the gamemap by setting {@link Square#hasWorkerOn()}
     *
     */
    @Override
    public void doAction() {

        //Aggiorno la posizione del worker
        oldPositionSquare.freeSquare();
        playerWorker.setPosition(newPosition);

        //Aggiorno la mappa di gioco
        newPositionSquare.setWorkerOn(playerWorker);



    }
}
