package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Map.Square;

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
