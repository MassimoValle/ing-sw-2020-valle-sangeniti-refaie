package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.God.Power;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class MoveActionStrategy implements ActionStrategy {

    private final Player player;
    private final Worker playerWorker;
    private final Position newPosition;

    public MoveActionStrategy(Player player, Worker playerWorker, Position newPosition) {
        this.player = player;
        this.playerWorker = playerWorker;
        this.newPosition = newPosition;
    }

    @Override
    public void doAction() {

        //Do something

    }
}
