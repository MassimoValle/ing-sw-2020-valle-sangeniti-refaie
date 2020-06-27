package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;

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