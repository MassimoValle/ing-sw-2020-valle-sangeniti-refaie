package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class AthenaPower extends Power {


    boolean hasGoneUp;

    public AthenaPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public boolean[] move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        int a = Game.getGameIntance().getGameMap().getSquare(positionWhereToMove).getHeight();
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
        if (hasGoneUp && moveAction.oldPositionSquare.getHeight() - moveAction.newPositionSquare.getHeight() < 0) {
            return true;
        }
        return false;
    }
}
