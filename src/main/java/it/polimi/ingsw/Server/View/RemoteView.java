package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Network.Connection;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.ClientRequests.Request;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            MasterController.dispatcher((Request) message);
        }

    }




    private final Connection connection;




    public RemoteView(Player player, Connection c){
        super(player);
        this.connection = c;

        c.addObserver(new MessageReceiver());
    }




    @Override
    protected void sendMessage(Game game) {

        Message response = game.notifyPlayer(getPlayer());

        if(response != null)
            connection.sendMessage(response);

    }
}

