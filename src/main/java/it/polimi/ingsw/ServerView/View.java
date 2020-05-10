package it.polimi.ingsw.ServerView;

import it.polimi.ingsw.Controller.TurnManager;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;

public abstract class View extends Observable<TurnManager> implements Observer<Game> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected abstract void showGame(Game game);


    @Override
    public void update(Game message) {
        showGame(message);
    }
}

