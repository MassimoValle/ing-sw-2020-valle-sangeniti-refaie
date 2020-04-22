package it.polimi.ingsw.Model.Action;


import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class BuildAction implements Action {

    private final Player player;
    private final Worker playerWorker;
    private final Position newPosition;

    public BuildAction(Player player, Worker playerWorker, Position newPosition) {
        this.player = player;
        this.playerWorker = playerWorker;
        this.newPosition = newPosition;
    }


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void doAction() {

        //Do something

    }
}
