package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.ActionOutcome;
import it.polimi.ingsw.Model.Map.Square;

public class DemeterPower extends Power {

    static boolean firstBuild;
    static Square firstBlockBuilt;


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
            if (outcome == ActionOutcome.DONE_CAN_BE_DONE_AGAIN) {
                firstBuild = false;
                firstBlockBuilt = squareWhereToBuild;
                return outcome;
            }

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

        //non raggiungibile
        return outcome;
    }
}
