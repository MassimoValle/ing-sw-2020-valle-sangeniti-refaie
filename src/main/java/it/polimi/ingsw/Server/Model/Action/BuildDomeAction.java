package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

/**
 * The BuildDomeAction is used only if ATLAS is in game and a player wants to build a Dome
 * .
 */
public class BuildDomeAction implements Action{

    private Square squareWhereToBuildOn;
    private Square squareWhereTheWorkerIs;


    public BuildDomeAction(Square squareWhereTheWorkerIs, Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
        this.squareWhereTheWorkerIs = squareWhereTheWorkerIs;
    }


    @Override
    public boolean isValid() {

        ArrayList<Position> adjacent = squareWhereTheWorkerIs.getPosition().getAdjacentPlaces();

        return !squareWhereToBuildOn.hasWorkerOn() && adjacent.contains(squareWhereToBuildOn.getPosition()) &&
                !squareWhereToBuildOn.hasDome();

    }

    @Override
    public void doAction() {

        try {
            squareWhereToBuildOn.addBlock(true);
        } catch (DomePresentException e) {
            e.printStackTrace();
        }


    }
}
