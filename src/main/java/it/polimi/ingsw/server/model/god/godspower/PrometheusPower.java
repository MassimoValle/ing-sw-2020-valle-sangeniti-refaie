package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrometheusPower extends Power implements Serializable {

    private transient boolean buildBefore = false;

    public PrometheusPower(String powerType, String powerDescription, GameMap map) {
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
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {

        if (!buildBefore)
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);



        Position startingPosition = squareWhereTheWorkerIs.getPosition();

        ArrayList<Position> reachables = (ArrayList<Position>) map.getReachableAdjacentPlaces(startingPosition);
        reachables.remove(squareWhereToBuild.getPosition());

        if ( map.forcedToMoveUp(reachables, startingPosition) )
            return ActionOutcome.NOT_DONE;
        else
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);



    }

    @Override
    public boolean canBuildBeforeMoving(Worker workerSelected) {

        List<Position> availablePosition = map.getReachableAdjacentPlaces(workerSelected.getPosition());
        Square workerSquare = map.getSquare(workerSelected.getPosition());


        if (!availablePosition.isEmpty()) {
            return map.squareMinusOneAvailable(workerSquare) || map.twoSquaresSameHeightAvailable(workerSquare)
                    || map.prometheusBuildsFirst(workerSquare);
        }
        return false;

    }

    @Override
    public void setBuildBefore() {
        buildBefore = true;
    }

    @Override
    public boolean canUsePowerBeforeMoving() {
        return true;
    }
}
