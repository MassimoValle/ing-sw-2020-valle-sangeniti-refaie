package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;

/**
 * The MoveAction update the workers position & the gameMap
 */
public class MoveAction implements Action {


    private final Power godsPowerPerformingAction;
    public final Worker playerWorker;
    public final Position newPosition;
    public final Square oldPositionSquare;
    public final Square newPositionSquare;



    public MoveAction(Power godsPowerPerformingAction, Worker playerWorker, Position newPosition, Square oldPositionSquare, Square newPositionSquare) {
        this.playerWorker = playerWorker;
        this.newPosition = newPosition;
        this.oldPositionSquare = oldPositionSquare;
        this.newPositionSquare = newPositionSquare;
        this.godsPowerPerformingAction = godsPowerPerformingAction;
    }

    /**
     *This method chek if the {@link Action} is valid
     *
     * @return true if is valid, false otherwise
     */
    @Override
    public boolean isValid() {

        //if some God Power is active that prevent you from doing this move
        if(godsPowerActive(godsPowerPerformingAction) || newPositionSquare.hasWorkerOn()) {
            return false;
        }

        return true;
    }



    /**
     * Move the selected {@link Worker playerWorker}  by {@link Player player} into the {@link Position newPosition} chose by the player;
     * Also update the gamemap by setting {@link Square#hasWorkerOn()}
     */
    @Override
    public void doAction() {

        //Aggiorno la posizione del worker
        oldPositionSquare.freeSquare();
        playerWorker.setPosition(newPosition);

        //Aggiorno la mappa di gioco
        newPositionSquare.setWorkerOn(playerWorker);

    }



    /**
     * Check if this {@link MoveAction} can be prevented from some other players' gods power
     *
     * @return true if action not permitted, false otherwise
     */
    private boolean godsPowerActive(Power godsPowerPerformingAction) {

        ArrayList<Power> powersInGame = (ArrayList<Power>) Game.getInstance().getPowersInGame();
        for (Power godPower: powersInGame) {
            if (godPower!= godsPowerPerformingAction && godPower.canPreventsFromPerformingAction() && godPower.checkIfActionNotPermitted(this)) {
                 return true;
            }
        }
        return false;
    }

}
