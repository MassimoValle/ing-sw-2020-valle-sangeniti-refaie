package it.polimi.ingsw.Network.Message.Server.Responses;


import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.Message;

public class Response extends Message {

    private String gameManagerSays;
    private ResponseContent responseContent;

    public Response(String messageSender, ResponseContent responseContent, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, messageStatus);
        this.responseContent = responseContent;
        this.gameManagerSays = gameManagerSays;
    }

    public String getGameManagerSays() {
        return gameManagerSays;
    }

    public ResponseContent getResponseContent() {
        return responseContent;
    }

    public MessageStatus getResponseStatus() {
        return super.getMessageStatus();
    }
}
