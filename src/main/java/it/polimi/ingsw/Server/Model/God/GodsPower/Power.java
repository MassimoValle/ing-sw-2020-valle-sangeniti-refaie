package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.BuildAction;
import it.polimi.ingsw.Server.Model.Action.BuildDomeAction;
import it.polimi.ingsw.Server.Model.Action.MoveAction;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.PowerType;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Power implements Serializable, GodsChecker {

    protected String powerDescription;
    protected PowerType powerType;


    protected Power(String powerType, String powerDescription) {
        this.powerType = PowerType.matchFromXml(powerType);
        this.powerDescription = powerDescription;
    }

    protected PowerType getPowerType() {
        return powerType;
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    /**
     * It creates a new {@link MoveAction} and run it;
     *
     * @param activeWorker           the active worker
     * @param positionWhereToMove    the position where to move
     * @param squareWhereTheWorkerIs the square where the worker is
     * @param squareWhereToMove      the square where to move
     * @return a boolean array containing the required info for the evolution of the game/turn
     */
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        Action moveAction = new MoveAction(this, activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

        if ( moveAction.isValid() ) {
            moveAction.doAction();
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }

    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {

        Action buildAction = new BuildAction(squareWhereTheWorkerIs, squareWhereToBuild);

        if (buildAction.isValid() ) {
            buildAction.doAction();
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }

    public ActionOutcome buildDome(Square squareWhereTheWorkerIs,Square squareWhereToBuild) {

        Action buildDomeAction = new BuildDomeAction(squareWhereTheWorkerIs, squareWhereToBuild);

        if (buildDomeAction.isValid()) {
            buildDomeAction.doAction();
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }
    }

    public boolean isWorkerStuck(Worker worker) {
        GameMap gameMap = Game.getInstance().getGameMap();

        ArrayList<Position> placesWhereToMove = gameMap.getReachableAdjacentPlaces(worker.getWorkerPosition());

        if (placesWhereToMove.isEmpty()) return true;

        for (Position position: placesWhereToMove ) {
            ArrayList<Position> placesWhereYouCanBuildOn = gameMap.getPlacesWhereYouCanBuildOn(position);
            if (!placesWhereYouCanBuildOn.isEmpty()) return false;
        }

        return true;
    }



    @Override
    public boolean canPreventsFromPerformingAction(){
        return false;
    }

    @Override
    public boolean checkIfActionNotPermitted(MoveAction moveAction) {
        return false;
    }

    @Override
    public boolean canBuildBeforeMoving(Worker workerSelected) {
        return false;
    }

    @Override
    public boolean canUsePowerBeforeMoving() {
        return false;
    }

    @Override
    public boolean canUsePowerBeforeBuilding() {
        return false;
    }

    @Override
    public boolean canBuildDomeAtAnyLevel() {
        return false;
    }

    @Override
    public void setBuildBefore() {
    }

    public boolean powerMustBeReset() {
        return false;
    }

    @Override
    public void resetPower() {

    }


    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Power)) {
            return false;
        }

        Power power = (Power) obj;

        return powerDescription.equals(power.getPowerDescription()) &&
                powerType.equals(power.getPowerType());
    }
}