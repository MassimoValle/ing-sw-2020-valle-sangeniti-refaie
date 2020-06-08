package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.MoveAction;
import it.polimi.ingsw.Server.Model.Player.Worker;

public interface GodsChecker {

    boolean checkIfActionNotPermitted(MoveAction moveAction);

    boolean canPreventsFromPerformingAction();

    boolean canBuildBeforeMoving(Worker workerSelected);

    boolean canUsePowerBeforeMoving();

    boolean canUsePowerBeforeBuilding();

    boolean canBuildDomeAtAnyLevel();

    void setBuildBefore();

    void resetPower();
}
