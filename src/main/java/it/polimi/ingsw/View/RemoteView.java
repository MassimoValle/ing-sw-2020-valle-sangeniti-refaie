package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Connection;
import it.polimi.ingsw.Controller.TurnManager;
import it.polimi.ingsw.Network.Message.MessageStatus;
import it.polimi.ingsw.Network.Message.Response;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try{
                // è il mio turno
                TurnManager turnManager = new TurnManager(RemoteView.this.getPlayer());
                // do something

                // fine turno
                RemoteView.this.connection.sendMessage(
                        new Response("Is your turn!", MessageStatus.OK)
                );
            } catch (IllegalArgumentException e) {
                connection.sendMessage(
                        new Response("Error! Make your move", MessageStatus.ERROR)
                );
            }
        }
    }

    private Connection connection;
    private boolean fistPlayer;

    public RemoteView(Player player, String opponent, Connection c, boolean firstPlayer){
        super(player);
        this.fistPlayer = firstPlayer;
        this.connection = c;
        c.addObserver(new MessageReceiver());
        //c.asyncSend("Your opponent is: " + opponent + "\tMake your move");
        c.sendMessage(
                new Response("Your opponent is: ", MessageStatus.OK)
        );
        if(this.fistPlayer){
            // sei il primo, fai il tuo turno
            TurnManager turnManager = new TurnManager(this.getPlayer());
            // do something

            // fine turno
            c.sendMessage(
                    new Response("Is your turn!", MessageStatus.OK)
            );
        }
    }

    @Override
    protected void showGame(Game game1) {
        System.out.println("something");
        //connection.send(model.getOutcome(getPlayer()).printOutcome() + "\tMake your move");
    }
}

