package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.model.Map.Square;


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
            e.printStackTrace();
        }

    }
}
