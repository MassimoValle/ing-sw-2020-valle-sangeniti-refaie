package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class MoveAction implements Action {

    private final Player player;
    private final Worker playerWorker;
    private final Position newPosition;

    public MoveAction(Player player, Worker playerWorker, Position newPosition) {
        this.player = player;
        this.playerWorker = playerWorker;
        this.newPosition = newPosition;
    }

    @Override
    public boolean isValid() {
        return true;
    }


    /**
     * Move the selected {@link Worker worker}  by {@link Player player} into the {@link Position position} chose by the player;
     *
     */
    @Override
    public void doAction() {

        Game.getInstance().getGameMap().setWorkerPosition(this.playerWorker, this.newPosition);


    }
}
