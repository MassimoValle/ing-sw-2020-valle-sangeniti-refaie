package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;

/**
 * This class is used to implement the ZeusBuild which allow the player to build under himself
 */
public class ZeusBuildAction extends BuildAction{

    public ZeusBuildAction(Square squareWhereToBuild) {
        super(squareWhereToBuild, squareWhereToBuild);
    }

    @Override
    public boolean isValid(GameMap map) {
        return true;
    }


}
