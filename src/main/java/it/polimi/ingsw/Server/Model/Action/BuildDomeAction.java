package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.Square;

/**
 * The BuildDomeAction is used only if ATLAS is in game and a player wants to build a Dome
 * .
 */
public class BuildDomeAction implements Action{

    private Square squareWhereToBuildOn;


    public BuildDomeAction(Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
    }


    @Override
    public boolean isValid() {
        if (squareWhereToBuildOn.hasWorkerOn()) {
            return false;
        }

        //IL CONTROLLO PER VEDERE SE HAI ATLAS (permette di costruire una dome a qualsiasi livello) VIENE FATTO NEL CONTROLLER

        return true;
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
