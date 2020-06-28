package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.Square;


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
