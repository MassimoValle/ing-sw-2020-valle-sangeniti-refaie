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
    };




    @Override
    protected void sendMessage(Message message) {

        if(message != null)
            connection.sendMessage(message);

    }
}

