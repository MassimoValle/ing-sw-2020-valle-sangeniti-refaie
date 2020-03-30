package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try{
                //Choice choice = Choice.parseInput(message);
                //processChoice(choice);
            } catch (IllegalArgumentException e) {
                connection.send("Error! Make your move");
            }
        }
    }

    private Connection connection;

    public RemoteView(Player player, String opponent, Connection c){
        super(player);
        this.connection = c;
        c.addObserver(new MessageReceiver());
        //c.asyncSend("Your opponent is: " + opponent + "\tMake your move");
        c.send("Your opponent is: " + opponent + "\tMake your move");
    }

    @Override
    protected void showModel(Model model) {
        System.out.println("something");
        //connection.send(model.getOutcome(getPlayer()).printOutcome() + "\tMake your move");
    }
}

