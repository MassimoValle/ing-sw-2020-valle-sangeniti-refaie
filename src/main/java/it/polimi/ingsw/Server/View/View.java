package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Controller.TurnManager;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;

public abstract class View extends Observable<TurnManager> implements Observer<Message> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected abstract void sendMessage(Message game);


    @Override
    public void update(Message message) {
        sendMessage(message);
    }

}

