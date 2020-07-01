package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.action.MoveAction;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

public class AthenaPower extends Power {


    private transient boolean goneUp;

    public AthenaPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription);
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
