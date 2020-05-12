package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.MoveAction;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

public class AthenaPower extends Power {


    boolean hasGoneUp;

    public AthenaPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        hasGoneUp = false;

        int a = squareWhereTheWorkerIs.getHeight();
        int b = squareWhereToMove.getHeight();
        if (b - a == 1) {
            hasGoneUp = true;
        }
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public boolean canPreventsFromPerformingAction() {
        return true;
    }

    @Override
    public boolean checkIfActionNotPermitted(MoveAction moveAction) {
        return hasGoneUp && moveAction.oldPositionSquare.getHeight() - moveAction.newPositionSquare.getHeight() < 0;
    }
}
