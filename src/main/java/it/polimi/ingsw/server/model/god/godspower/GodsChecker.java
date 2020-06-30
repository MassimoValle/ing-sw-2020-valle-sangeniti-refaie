package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.MoveAction;
import it.polimi.ingsw.server.model.player.Worker;

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
