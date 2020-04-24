package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Map.Square;


public class BuildAction implements Action {
    private Square squareWhereToBuildOn;


    public BuildAction(Square squareWhereToBuildOn) {
        this.squareWhereToBuildOn = squareWhereToBuildOn;
    }


    @Override
    public boolean isValid() {
        if (squareWhereToBuildOn.hasWorkerOn()) {
            return false;
        }

        return true;
    }

    @Override
    public void doAction() {

        squareWhereToBuildOn.addBlock();

    }
}
