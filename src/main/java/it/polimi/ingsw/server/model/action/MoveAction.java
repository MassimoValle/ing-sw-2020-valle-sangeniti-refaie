package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.god.GodsInGame;
import it.polimi.ingsw.server.model.god.godspower.Power;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.util.ArrayList;

/**
 * The MoveAction update the workers position and the gameMap
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
    public boolean isValid(GameMap map) {

        int heightDifference = map.getDifferenceInAltitude(oldPositionSquare.getPosition(), newPositionSquare.getPosition());
        ArrayList<Position> adjacent = oldPositionSquare.getPosition().getAdjacentPlaces();

        //if some God Power is active that prevent you from doing this move
        return checkGodsPowerActive(godsPowerPerformingAction, map) && !newPositionSquare.hasWorkerOn() &&
                heightDifference >= -1 && !newPositionSquare.hasDome() &&
                adjacent.contains(newPosition);
    }



    /**
     * Move the selected {@link Worker playerWorker}  by {@link Player player} into the {@link Position newPosition} chose by the player;
     * Also update the gamemap by setting {@link Square#hasWorkerOn()}
     */
    @Override
    public void doAction() {

        oldPositionSquare.freeSquare();
        playerWorker.setPosition(newPosition);

        newPositionSquare.setWorkerOn(playerWorker);

    }



    /**
     * Check if this {@link MoveAction} can be prevented from some other players' gods power
     *
     * @return true if action not permitted, false otherwise
     */
    boolean checkGodsPowerActive(Power godsPowerPerformingAction, GameMap map) {

        ArrayList<Power> powersInGame = GodsInGame.getIstance().getPowersByMap(map);
        for (Power godPower: powersInGame) {
            if (!godPower.equals(godsPowerPerformingAction) && godPower.canPreventsFromPerformingAction() && godPower.checkIfActionNotPermitted(this)) {
                 return false;
            }
        }
        return true;
    }

    /**
     * It checks if moving from a lvl2 to lvl3 block
     *
     * @return true if so, false otherwise
     */
    public boolean winningMove() {
        return oldPositionSquare.getHeight() == 2 && newPositionSquare.getHeight() == 3;
    }
}
