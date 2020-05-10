package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;

public class DemeterPower extends Power {

    private static boolean firstBuild;
    private static Square firstBlockBuilt;


    public DemeterPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstBuild = true;
        firstBlockBuilt = null;
    }

    @Override
    public ActionOutcome build(Square squareWhereToBuild) {
        ActionOutcome outcome;

        if (firstBlockBuilt == null) {
            outcome = super.build(squareWhereToBuild);
            if (outcome == ActionOutcome.DONE) {
                firstBuild = false;
                firstBlockBuilt = squareWhereToBuild;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else return ActionOutcome.NOT_DONE;

        } else if (firstBlockBuilt.equals(squareWhereToBuild)) {
            return ActionOutcome.NOT_DONE;
        } else {
            outcome = super.build(squareWhereToBuild);
            if (outcome == ActionOutcome.DONE) {
                //resetto
                firstBuild = true;
                firstBlockBuilt = squareWhereToBuild;
                return outcome;
            } else {
                return ActionOutcome.NOT_DONE;
            }
        }


    }
}
