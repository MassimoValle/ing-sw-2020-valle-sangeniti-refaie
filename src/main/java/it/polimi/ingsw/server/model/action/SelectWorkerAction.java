package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.god.godspower.Power;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Worker;


/**
 * The Select worker action mark the Worker as SELECTED
 */
public class SelectWorkerAction implements Action{

    private final Worker workerToSelect;
    private final Player player;
    private final Power power;

    public SelectWorkerAction(Power power, Worker workerToSelect, Player activePlayer) {
        this.workerToSelect = workerToSelect;
        this.player = activePlayer;
        this.power = power;
    }

    /**
     *  It doesn't really has to check for nothing here
     *
     * @return always true
     */
    @Override
    public boolean isValid(GameMap map) {
        return workerToSelect.getOwner().equals(player) && !power.isWorkerStuck(workerToSelect);
    }

    /**
     *  Set the {@link Worker#isSelected()} true
     *
     */
    @Override
    public void doAction() {

        this.workerToSelect.setSelected(true);

    }
}
