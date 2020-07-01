package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.Square;

public class AtlasPower extends Power {

    public AtlasPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome buildDome(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {
        return super.buildDome(squareWhereTheWorkerIs, squareWhereToBuild);
    }

    @Override
    public boolean canUsePowerBeforeBuilding() {
        return true;
    }

    @Override
    public boolean canBuildDomeAtAnyLevel() {
        return true;
    }

}