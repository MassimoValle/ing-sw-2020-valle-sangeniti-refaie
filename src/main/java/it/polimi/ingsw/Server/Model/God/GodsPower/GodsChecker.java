package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.MoveAction;
import it.polimi.ingsw.Server.Model.Player.Worker;

public interface GodsChecker {

    public boolean checkIfActionNotPermitted(MoveAction moveAction);

    //used for Athena
    public boolean canPreventsFromPerformingAction();

    //used for prometheus
    public boolean canBuildBeforeMoving(Worker workerSelected);


    //used for prometheus
    public void setBuildBefore();
}
