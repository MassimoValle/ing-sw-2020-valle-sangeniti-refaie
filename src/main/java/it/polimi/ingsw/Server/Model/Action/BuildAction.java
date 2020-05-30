package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;


/**
 * The BuildAction is called whenever a player wants to build every kind of level
 * The construction of a Block/Dome is transparent to the player who wants to build
 */
public class BuildAction implements Action {
    private Square squareWhereToBuildOn;
    private Square squareWhereTheWorkerIs;


    public BuildAction(Square squareWhereTheWorkerIs, Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
        this.squareWhereTheWorkerIs = squareWhereTheWorkerIs;
    }


    @Override
    public boolean isValid() {

        ArrayList<Position> adjacent = squareWhereTheWorkerIs.getPosition().getAdjacentPlaces();

        return !squareWhereToBuildOn.hasWorkerOn() && adjacent.contains(squareWhereToBuildOn.getPosition()) &&
                !squareWhereToBuildOn.hasDome();
    }


    public boolean clientValidation() {

        ArrayList<Position> adjacent = squareWhereTheWorkerIs.getPosition().getAdjacentPlaces();

        return !squareWhereToBuildOn.hasWorkerOn() && adjacent.contains(squareWhereToBuildOn.getPosition()) &&
                !squareWhereToBuildOn.hasDome();
    }

    @Override
    public void doAction() {

        try {
            squareWhereToBuildOn.addBlock(false);
        } catch (DomePresentException e) {
            e.printStackTrace();
        }

    }
}
