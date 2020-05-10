package it.polimi.ingsw.ServerView;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Connection;
import it.polimi.ingsw.Network.Message.Message;

public class RemoteView extends View {

    /*private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try{
                // è il mio turno
                //TurnManager turnManager = new TurnManager(RemoteView.this.getPlayer());
                // do something

                // fine turno
                RemoteView.this.connection.sendMessage(
                        new Message("[SERVER]", MessageStatus.OK, "Is your turn!")
                );
            } catch (IllegalArgumentException e) {
                connection.sendMessage(
                        new Message("[SERVER]", MessageStatus.ERROR, "Error! Make your move")
                );
            }
        }
    }*/

    private Connection connection;

    public RemoteView(Player player, Connection c){
        super(player);
        this.connection = c;
        //c.addObserver(new MessageReceiver());
        //c.asyncSend("Your opponent is: " + opponent + "\tMake your move");
        /*c.sendMessage(
                new Response("Your opponent is: ", MessageStatus.OK)
        );*/
    }

    @Override
    protected void showGame(Game game) {

        // !!! il model è stato modificato
        Message response = game.notifyPlayer(getPlayer());

        if(response != null)
            connection.sendMessage(response);



        //connection.send(model.getOutcome(getPlayer()).printOutcome() + "\tMake your move");
    }
}

