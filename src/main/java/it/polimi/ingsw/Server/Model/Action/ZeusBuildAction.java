package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;

public class ZeusBuildAction extends BuildAction{

    public ZeusBuildAction(Square squareWhereTheWorkerIs, Square squareWhereToBuildOn) {
        super(squareWhereTheWorkerIs, squareWhereToBuildOn);
    }

    public ZeusBuildAction(Square squareWhereToBuild) {
        super(squareWhereToBuild, squareWhereToBuild);
    }

    @Override
    public boolean isValid(GameMap map) {
        return true;
    }

    @Override
    public boolean clientValidation() {
        return true;
    }


}
