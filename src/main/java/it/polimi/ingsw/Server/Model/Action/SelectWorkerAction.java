package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Worker;


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
     * @return always true
     */
    @Override
    public void doAction() {

        this.workerToSelect.setSelected(true);

    }
}
