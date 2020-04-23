package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class MoveAction implements Action {

    private final Player player;
    private final Worker playerWorker;
    private final Position newPosition;
    private Square square;


    public MoveAction(Player player, Worker playerWorker, Position newPosition, Square square) {
        this.player = player;
        this.playerWorker = playerWorker;
        this.newPosition = newPosition;
        this.square = square;
    }

    @Override
    public boolean isValid() {
        if (square.hasWorkerOn()) {
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
        playerWorker.setPosition(newPosition);

        //Aggiorno la mappa di gioco
        square.setWorkerOn(playerWorker);



    }
}
