package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrometheusPower extends Power implements Serializable {

    private boolean buildBefore = false;

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
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {

        if (!buildBefore)
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);



        GameMap map = Game.getInstance().getGameMap();
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
        GameMap gameMap = Game.getInstance().getGameMap();

        List<Position> availablePosition = gameMap.getReachableAdjacentPlaces(workerSelected.getPosition());
        Square workerSquare = gameMap.getSquare(workerSelected.getPosition());


        if (!availablePosition.isEmpty()) {
            return gameMap.squareMinusOneAvailable(workerSquare) || gameMap.twoSquaresSameHeightAvailable(workerSquare)
                    || gameMap.prometheusBuildsFirst(workerSquare);
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
