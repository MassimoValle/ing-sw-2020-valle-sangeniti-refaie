package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;

/**
 * The {@link Request} class is needed to split every possible move from the player.
 */
public class Request extends Message{


    public Request(String messageSender, String message) {
        super(messageSender, message);

    }


    public Request(String messageSender) {
        super(messageSender);
    }
}
