package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.*;
import it.polimi.ingsw.server.model.god.GodsInGame;
import it.polimi.ingsw.server.model.god.PowerType;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Power implements Serializable, GodsChecker {

    protected String powerDescription;
    protected PowerType powerType;
    protected transient GameMap map;


    protected Power(String powerType, String powerDescription) {
        this.powerType = PowerType.matchFromXml(powerType);
        this.powerDescription = powerDescription;
        this.map = null;
    }

    protected PowerType getPowerType() {
        return powerType;
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    public ActionOutcome selectWorker(Worker workerFromRequest, Player requestSender) {

        Action selectWorkerAction = new SelectWorkerAction(this, workerFromRequest, requestSender);

        if ( selectWorkerAction.isValid(map) ) {
            selectWorkerAction.doAction();
            return ActionOutcome.DONE;
        } else
            return ActionOutcome.NOT_DONE;

    }

    public void setMap(GameMap gameMap) {
        this.map = gameMap;
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

        if ( moveAction.isValid(map) ) {
            moveAction.doAction();
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }

    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {

        Action buildAction = new BuildAction(squareWhereTheWorkerIs, squareWhereToBuild);

        if (buildAction.isValid(map) ) {
            buildAction.doAction();
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }

    public ActionOutcome buildDome(Square squareWhereTheWorkerIs,Square squareWhereToBuild) {

        Action buildDomeAction = new BuildDomeAction(squareWhereTheWorkerIs, squareWhereToBuild);

        if (buildDomeAction.isValid(map)) {
            buildDomeAction.doAction();
            return ActionOutcome.DONE;
        } else {
            return ActionOutcome.NOT_DONE;
        }
    }


    /**
     * It checks if the {@link Worker worker} has no reachable places
     *
     * @param worker the worker
     * @return true if worker is stuck, false otherwise
     */
    public boolean isWorkerStuck(Worker worker) {
        ArrayList<Position> reachable = (ArrayList<Position>) map.getReachableAdjacentPlaces(worker.getPosition());

        if (!reachable.isEmpty() && map.forcedToMoveUp(reachable, worker.getPosition()) && athenaPowerActivated())
            return true;

        return reachable.isEmpty();
    }


    protected boolean athenaPowerActivated() {
        for (Power power : GodsInGame.getIstance().getPowersByMap(map)) {
            if (power instanceof AthenaPower && ((AthenaPower) power).hasGoneUp())
                return true;
        }
        return false;
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
                powerType == power.getPowerType();
    }


    @Override
    public int hashCode() {
        int result = getPowerDescription().hashCode();
        result = 31 * result + getPowerType().hashCode();
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }
}