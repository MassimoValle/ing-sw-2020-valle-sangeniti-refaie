package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.action.ZeusBuildAction;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.io.Serializable;

public class ZeusPower extends Power implements Serializable {

    private transient Position newZeusPosition = null;

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

            if ( zeusBuildAction.isValid(null) )
                zeusBuildAction.doAction();

            return ActionOutcome.DONE;
        }

        return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
    }
}
