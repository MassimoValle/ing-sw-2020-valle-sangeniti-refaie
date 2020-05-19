package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;
import java.util.ArrayList;

public class PrometheusPower extends Power implements Serializable {

    private static boolean buildBefore = false;

    public PrometheusPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }


    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if (buildBefore && squareWhereTheWorkerIs.getHeight() - squareWhereToMove.getHeight() < 0 ) {
                return ActionOutcome.NOT_DONE;
        }

        ActionOutcome outcome = super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        if (outcome == ActionOutcome.DONE) {
            if (buildBefore) buildBefore = false;
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }


    @Override
    public boolean canBuildBeforeMoving(Worker workerSelected) {
        ArrayList<Position> availablePosition = Game.getInstance().getGameMap().getReachableAdjacentPlaces(workerSelected.getWorkerPosition());

        if (availablePosition.isEmpty()) {
            return false;
        } else if ( availablePosition.size() == 1) {
            Square singleSquareAvailable = Game.getInstance().getGameMap().getSquare(availablePosition.get(0));
            Square workerSquare = Game.getInstance().getGameMap().getSquare(workerSelected.getWorkerPosition());
            return workerSquare.getHeight() - singleSquareAvailable.getHeight() >= 1;
        }

        return true;
    }

    @Override
    public void setBuildBefore() {
        buildBefore = true;
    }
}
