package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientrequests.Request;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            masterController.dispatcher((Request) message);
        }

    }




    private final Connection connection;
    private MasterController masterController;




    public RemoteView(Player player, Connection c){
        super(player);
        this.connection = c;

        c.addObserver(new MessageReceiver());
    }


    public void setController(MasterController masterController){
        this.masterController = masterController;
    }


    @Override
    protected void sendMessage(Message message) {

        if(message != null)
            connection.sendMessage(message);

    }
}

