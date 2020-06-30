package it.polimi.ingsw.network.message.server.serverrequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.network.message.server.ServerMessage;

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
