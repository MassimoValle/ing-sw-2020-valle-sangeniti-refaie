package it.polimi.ingsw.Model.Action;


import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class BuildAction implements Action {

    private final Player workersOwner;
    private final Worker workerToUseToBuild;
    private final Position positionWhereToBuildOn;
    private Square squareWhereToBuildOn;


    public BuildAction(Player workersOwner, Worker workerToUseToBuild, Position positionWhereToBuildOn, Square squareWhereToBuildOn) {
        this.workersOwner = workersOwner;
        this.workerToUseToBuild = workerToUseToBuild;
        this.positionWhereToBuildOn = positionWhereToBuildOn;
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

        squareWhereToBuildOn.heightPlusOne();

    }
}
