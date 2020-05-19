package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;

public class ZeusPower extends Power implements Serializable {

    private Position newZeusPosition = null;

    public ZeusPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        newZeusPosition = positionWhereToMove;
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public ActionOutcome build(Square squareWhereToBuild) {

        if(newZeusPosition.equals(squareWhereToBuild.getPosition()) && squareWhereToBuild.getHeight() < 3) {
            Worker zeusWorker = squareWhereToBuild.getWorkerOnSquare();
            squareWhereToBuild.freeSquare();
            super.build(squareWhereToBuild);
            squareWhereToBuild.setWorkerOn(zeusWorker);
            return ActionOutcome.DONE;
        }

        return super.build(squareWhereToBuild);
    }
}
