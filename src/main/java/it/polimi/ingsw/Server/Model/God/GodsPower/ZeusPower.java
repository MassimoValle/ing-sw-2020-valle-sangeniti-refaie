package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.ZeusBuildAction;
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
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {

        if(newZeusPosition.equals(squareWhereToBuild.getPosition()) && squareWhereToBuild.getHeight() < 3) {

            ZeusBuildAction zeusBuildAction = new ZeusBuildAction(squareWhereToBuild);

            if ( zeusBuildAction.isValid() )
                zeusBuildAction.doAction();

            return ActionOutcome.DONE;
        }

        return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
    }
}
