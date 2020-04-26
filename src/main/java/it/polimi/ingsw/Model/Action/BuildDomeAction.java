package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Map.Square;

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

        squareWhereToBuildOn.addBlock(true);


    }
}
