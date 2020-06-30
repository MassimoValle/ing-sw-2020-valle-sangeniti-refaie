package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.server.model.map.Square;


/**
 * The BuildDomeAction is used only if ATLAS is in game and a player wants to build a Dome
 * .
 */
public class BuildDomeAction extends BuildAction{


    public BuildDomeAction(Square squareWhereTheWorkerIs, Square squareWhereToBuildOn) {
        super(squareWhereTheWorkerIs, squareWhereToBuildOn);
    }


    @Override
    public void doAction() {

        try {
            squareWhereToBuildOn.addBlock(true);
        } catch (DomePresentException e) {
            Server.LOGGER.severe("Misalignment GameMap, build hasn't been done");
            Thread.currentThread().interrupt();
        }

    }
}
