package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.PlayerTurn;
import it.polimi.ingsw.Model.Player.Player;

public abstract class View extends Observable<PlayerTurn> implements Observer<Game> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected abstract void showGame(Game game1);


    @Override
    public void update(Game message) {
        showGame(message);
    }
}

