package it.polimi.ingsw.server.view;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.model.Player.Player;

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

