package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;

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


}
