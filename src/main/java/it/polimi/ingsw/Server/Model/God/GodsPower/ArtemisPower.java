package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;

public class ArtemisPower extends Power implements Serializable {

    private boolean firstMove;
    private Square startingPlace = null;

    private ActionOutcome outcome;

    public ArtemisPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        this.firstMove = true;
    }



    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if (startingPlace == null) {
            outcome = super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
            if (outcome == ActionOutcome.DONE && hasNewMoveAvailable(positionWhereToMove)) {
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

    private boolean hasNewMoveAvailable(Position pos) {
        GameMap map = Game.getInstance().getGameMap();

        return map.getReachableAdjacentPlaces(pos).size() > 1;
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
