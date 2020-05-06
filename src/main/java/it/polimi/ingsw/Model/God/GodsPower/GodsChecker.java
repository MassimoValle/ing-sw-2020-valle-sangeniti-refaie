package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.MoveAction;

public interface GodsChecker {

    public boolean checkIfActionNotPermitted(MoveAction moveAction);

    public boolean canPreventsFromPerformingAction();

}
