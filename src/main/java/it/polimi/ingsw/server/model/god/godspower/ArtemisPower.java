package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.io.Serializable;
import java.util.ArrayList;

public class ArtemisPower extends Power implements Serializable {

    private transient boolean firstMove;
    private transient Square startingPlace = null;

    private ActionOutcome outcome;

    public ArtemisPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription);
        this.firstMove = true;
    }



    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if (startingPlace == null) {
            outcome = super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
            if (outcome == ActionOutcome.DONE && hasNewMoveAvailable(squareWhereTheWorkerIs.getPosition(), positionWhereToMove) ) {
                firstMove = false;
                startingPlace = squareWhereTheWorkerIs;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else if (outcome == ActionOutcome.DONE) {
                return ActionOutcome.DONE;
            } else {
                return ActionOutcome.NOT_DONE;
            }


        } else if (startingPlace.equals(squareWhereToMove)) {
            return ActionOutcome.NOT_DONE;
        } else {
            return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        }

    }

    private boolean hasNewMoveAvailable(Position initPos, Position newPos) {

        ArrayList<Position> reachables = (ArrayList<Position>) map.getReachableAdjacentPlaces(newPos);
        reachables.remove(initPos);

        if (map.forcedToMoveUp(reachables, newPos))// && athenaPowerActivated())
            return false;

        return !reachables.isEmpty();

    }

    @Override
    public boolean powerMustBeReset() {
        return true;
    }

    @Override
    public void resetPower() {
        firstMove = true;
        startingPlace = null;
    }
}
