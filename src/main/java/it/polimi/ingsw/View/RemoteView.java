package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.PlayerTurn;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try{
                // è il mio turno
                PlayerTurn turn = new PlayerTurn(RemoteView.this.getPlayer());
                // do something

                // fine turno
                RemoteView.this.connection.send("Is your turn!");
            } catch (IllegalArgumentException e) {
                connection.send("Error! Make your move");
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
        c.send("Your opponent is: " + opponent);
        if(this.fistPlayer){
            // sei il primo, fai il tuo turno
            PlayerTurn turn = new PlayerTurn(this.getPlayer());
            // do something

            // fine turno
            c.send("Is your turn!");
        }
    }

    @Override
    protected void showGame(Game game1) {
        System.out.println("something");
        //connection.send(model.getOutcome(getPlayer()).printOutcome() + "\tMake your move");
    }
}

