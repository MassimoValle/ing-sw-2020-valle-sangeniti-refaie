package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.BuildAction;
import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.God.PowerType;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public abstract class Power {

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
     * @return true if worker can move again, false otherwise
     */
    public boolean[] move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        Action moveAction = new MoveAction(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

        if ( moveAction.isValid() ) {
            moveAction.doAction();
        } else {
            return actionNotDone();
        }

        return actionDone();
    }

    public boolean[] build(Square squareWhereToBuild) {

        Action buildAction = new BuildAction(squareWhereToBuild);

        if (buildAction.isValid() ) {
            buildAction.doAction();
        }

        return actionDone();
    }

    protected boolean[] actionDone() {
        boolean[] ret = new boolean[2];
        ret[0] = true;
        return ret;
    }

    protected boolean[] actionDoneCanBeDoneAgain() {
        boolean[] ret = new boolean[2];
        ret[0] = true;
        ret[1] = true;
        return ret;
    }

    protected boolean[] actionNotDone() {
        boolean[] ret = new boolean[2];
        return ret;
    }


}