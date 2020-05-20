package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.Square;


/**
 * The BuildAction is called whenever a player wants to build every kind of level
 * The construction of a Block/Dome is transparent to the player who wants to build
 */
public class BuildAction implements Action {
    private Square squareWhereToBuildOn;


    public BuildAction(Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
    }


    @Override
    public boolean isValid() {

        return !squareWhereToBuildOn.hasWorkerOn();
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
