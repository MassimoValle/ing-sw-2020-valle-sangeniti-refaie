package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Controller.TurnManager;
import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.BuildAction;
import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.God.PowerType;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

import java.io.Serializable;

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
            return actionDone();
        }

        return actionNotDone();
    }

    /**
     * After the action is validated and performed, it tells {@link it.polimi.ingsw.Controller.ActionManager} how to evolve
     * This specific method returns values so that if the {@link TurnManager#getActivePlayer()} has moved, then he must build,
     * in the same way if he has built, then he must end his turn
     *
     * @return a boolean array containing the required info for the evolution of the game/turn
     */
    protected boolean[] actionDone() {
        boolean[] ret = new boolean[2];
        ret[0] = true;
        return ret;
    }

    /**
     * After the action is validated and performed, it tells {@link it.polimi.ingsw.Controller.ActionManager} how to evolve
     * This specific method returns values so that if the {@link TurnManager#getActivePlayer()} has moved, then he can move again,
     * in the same way if he has built, then he can build again
     * @return a boolean array containing the required info for the evolution of the game/turn
     */
    protected boolean[] actionDoneCanBeDoneAgain() {
        boolean[] ret = new boolean[2];
        ret[0] = true;
        ret[1] = true;
        return ret;
    }

    /**
     * If the action is not validated, then this method make the {@link it.polimi.ingsw.Controller.ActionManager} to not evolve,
     * actually makes it to waits to receive a valid action from the {@link TurnManager#getActivePlayer()}
     *
     * @return a boolean array containing the required info for the evolution of the game/turn
     */
    protected boolean[] actionNotDone() {
        boolean[] ret = new boolean[2];
        return ret;
    }


    public boolean canPreventsFromPerformingAction(){
        return false;
    }

    @Override
    public boolean checkIfActionNotPermitted(MoveAction moveAction) {
        return false;
    }
}