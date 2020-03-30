package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.PlayerTurn;
import it.polimi.ingsw.Model.Player;

public abstract class View extends Observable<PlayerTurn> implements Observer<Model> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected abstract void showModel(Model model);


    @Override
    public void update(Model message) {
        showModel(message);
    }
}

