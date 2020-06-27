package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.MoveAction;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

public class AthenaPower extends Power {


    private static boolean goneUp;

    public AthenaPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        goneUp = false;

        int a = squareWhereTheWorkerIs.getHeight();
        int b = squareWhereToMove.getHeight();
        if (b - a == 1) {
            goneUp = true;
        }
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public boolean canPreventsFromPerformingAction() {
        return true;
    }

    @Override
    public boolean checkIfActionNotPermitted(MoveAction moveAction) {
        return goneUp && moveAction.oldPositionSquare.getHeight() - moveAction.newPositionSquare.getHeight() < 0;
    }

    public boolean hasGoneUp() {
        return goneUp;
    }
}
