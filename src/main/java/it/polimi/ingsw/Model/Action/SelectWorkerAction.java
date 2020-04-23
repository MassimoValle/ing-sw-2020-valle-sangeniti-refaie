package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Worker;

import java.util.List;

public class SelectWorkerAction implements Action{

    private Player workersOwner;
    private Worker workerToSelect;

    public SelectWorkerAction(Player workersOwner, Worker workerToSelect) {
        this.workersOwner = workersOwner;
        this.workerToSelect = workerToSelect;
    }

    /**
     *  It doesn't really has to check for nothing here
     *
     * @return always true
     */
    @Override
    public boolean isValid() {

        return true;

    }

    /**
     *  Set the {@link Worker#isSelected()} true
     *
     * @return always true
     */
    @Override
    public void doAction() {

        this.workerToSelect.setSelected(true);

    }
}
