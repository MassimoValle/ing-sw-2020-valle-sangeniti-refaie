package it.polimi.ingsw.network.message.server.serverresponse;


import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.network.message.server.ServerMessage;

/**
 * The serverResponse is used to respond whenever a {@link it.polimi.ingsw.network.message.clientrequests.Request } is sent by the client
 */
public class ServerResponse extends ServerMessage {

    private final String gameManagerSays;
    private final ResponseContent responseContent;

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
