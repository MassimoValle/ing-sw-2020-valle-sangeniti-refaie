package it.polimi.ingsw.server.model.God.GodsPower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.action.ZeusBuildAction;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Map.Square;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.server.model.Player.Worker;

import java.io.Serializable;

public class ZeusPower extends Power implements Serializable {

    private Position newZeusPosition = null;

    public ZeusPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
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

            if ( zeusBuildAction.isValid(null) )
                zeusBuildAction.doAction();

            return ActionOutcome.DONE;
        }

        return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
    }
}
