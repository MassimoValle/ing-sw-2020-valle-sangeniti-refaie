package it.polimi.ingsw.server.model.God.GodsPower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Map.Square;

public class AtlasPower extends Power {

    public AtlasPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
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