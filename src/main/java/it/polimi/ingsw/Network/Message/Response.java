package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameManager;

public class Response extends Message {

    private String gameManagerSays;

    public Response(String messageSender, String message, String gameManagerSays) {
        super(messageSender, message);
        this.gameManagerSays = gameManagerSays;
    }

    public Response(String messageSender, String gameManagerSays, MessageStatus messageStatus) {
        super(messageSender, messageStatus);
        this.gameManagerSays = gameManagerSays;
    }
}
