package it.polimi.ingsw.ServerView;

import it.polimi.ingsw.Controller.MasterController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Connection;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Requests.Request;

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
    protected void showGame(Game game) {

        Message response = game.notifyPlayer(getPlayer());

        if(response != null)
            connection.sendMessage(response);

    }
}

