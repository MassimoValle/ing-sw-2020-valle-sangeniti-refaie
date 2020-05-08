package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.ActionOutcome;
import it.polimi.ingsw.Model.Action.BuildDomeAction;
import it.polimi.ingsw.Model.Map.Square;

public class AtlasPower extends Power {

    public AtlasPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome buildDome(Square squareWhereToBuild) {
        return super.buildDome(squareWhereToBuild);
    }

}
