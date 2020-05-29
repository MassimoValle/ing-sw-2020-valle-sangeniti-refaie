package it.polimi.ingsw.Network.Message.Server.ServerResponse;


import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.Server.ServerMessage;

public class ServerResponse extends ServerMessage {

    private String gameManagerSays;
    private ResponseContent responseContent;

    public ServerResponse(ResponseContent responseContent, MessageStatus messageStatus, String gameManagerSays) {
        super(messageStatus);
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

    @Override
    public void execute() {
        super.getClientManager().handleServerResponse(this);
    }
}
