package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.ActionOutcome;
import it.polimi.ingsw.Model.Map.Square;

public class DemeterPower extends Power {

    public DemeterPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome build(Square squareWhereToBuild) {
        return super.build(squareWhereToBuild);
    }
}
