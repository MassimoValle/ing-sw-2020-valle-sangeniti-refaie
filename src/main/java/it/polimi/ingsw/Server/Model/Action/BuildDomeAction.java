package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

/**
 * The BuildDomeAction is used only if ATLAS is in game and a player wants to build a Dome
 * .
 */
public class BuildDomeAction implements Action{

    private final Square squareWhereToBuildOn;
    private final Square squareWhereTheWorkerIs;


    public BuildDomeAction(Square squareWhereTheWorkerIs, Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
        this.squareWhereTheWorkerIs = squareWhereTheWorkerIs;
    }


    @Override
    public boolean isValid(GameMap map) {

        ArrayList<Position> adjacent = squareWhereTheWorkerIs.getPosition().getAdjacentPlaces();

        return !squareWhereToBuildOn.hasWorkerOn() && adjacent.contains(squareWhereToBuildOn.getPosition()) &&
                !squareWhereToBuildOn.hasDome();

        //IL CONTROLLO PER VEDERE SE HAI ATLAS (permette di costruire una dome a qualsiasi livello) VIENE FATTO NEL CONTROLLER
    }

    @Override
    public void doAction() {

        try {
            squareWhereToBuildOn.addBlock(true);
        } catch (DomePresentException e) {
            e.printStackTrace();
        }


    }

    public boolean clientValidation() {
        ArrayList<Position> adjacent = squareWhereTheWorkerIs.getPosition().getAdjacentPlaces();

        return !squareWhereToBuildOn.hasWorkerOn() && adjacent.contains(squareWhereToBuildOn.getPosition()) &&
                !squareWhereToBuildOn.hasDome();
    }
}
