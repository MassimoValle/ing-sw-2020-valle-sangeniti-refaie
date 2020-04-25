package it.polimi.ingsw.Network.Message;


public class Response extends Message {

    private String gameManagerSays;

    public Response(String messageSender, MessageContent messageContent, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, messageContent, messageStatus);
        this.gameManagerSays = gameManagerSays;
    }

    public String getGameManagerSays() {
        return gameManagerSays;
    }
}
