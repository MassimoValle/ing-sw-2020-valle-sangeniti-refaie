package it.polimi.ingsw.network.message.server.serverrequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.network.message.server.ServerMessage;

/**
 * The serverRequest is used to ask explicitly that a particular answer from the client is requested
 */
public class ServerRequest extends ServerMessage {

    ServerRequestContent content;

    public ServerRequest(ServerRequestContent content) {
        super();
        this.content = content;
    }

    public ServerRequestContent getContent() {
        return content;
    }

    @Override
    public void execute() {
        super.getClientManager().handleServerRequest(this);
    }
}
