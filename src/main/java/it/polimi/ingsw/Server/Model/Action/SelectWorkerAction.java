package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Worker;


/**
 * The Select worker action mark the Worker as SELECTED
 */
public class SelectWorkerAction implements Action{

    private Worker workerToSelect;
    private Player player;

    public SelectWorkerAction(Worker workerToSelect, Player activePlayer) {
        this.workerToSelect = workerToSelect;
        this.player = activePlayer;
    }

    /**
     *  It doesn't really has to check for nothing here
     *
     * @return always true
     */
    @Override
    public boolean isValid() {
        return workerToSelect.getColor().equals(player.getColor());
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
