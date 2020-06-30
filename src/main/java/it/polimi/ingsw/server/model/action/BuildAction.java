package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;

import java.util.ArrayList;


/**
 * The BuildAction is called whenever a player wants to build every kind of level
 * The construction of a Block/Dome is transparent to the player who wants to build
 */
public class BuildAction implements Action {
    protected final Square squareWhereToBuildOn;
    protected final Square squareWhereTheWorkerIs;


    public BuildAction(Square squareWhereTheWorkerIs, Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
        this.squareWhereTheWorkerIs = squareWhereTheWorkerIs;
    }


    @Override
    public boolean isValid(GameMap map) {

        ArrayList<Position> adjacent = squareWhereTheWorkerIs.getPosition().getAdjacentPlaces();

        return !squareWhereToBuildOn.hasWorkerOn() && adjacent.contains(squareWhereToBuildOn.getPosition()) &&
                !squareWhereToBuildOn.hasDome();
    }



    @Override
    public void doAction() {

        try {
            squareWhereToBuildOn.addBlock(false);
        } catch (DomePresentException e) {
            Server.LOGGER.severe("Misalignment GameMap, build hasn't been done");
            Thread.currentThread().interrupt();
        }

    }
}
