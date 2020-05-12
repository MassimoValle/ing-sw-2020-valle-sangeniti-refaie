package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.MoveAction;

public interface GodsChecker {

    public boolean checkIfActionNotPermitted(MoveAction moveAction);

    public boolean canPreventsFromPerformingAction();

}
