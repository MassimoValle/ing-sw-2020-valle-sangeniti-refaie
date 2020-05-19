package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Network.Message.Message;

public class ServerRequest extends Message {

    ServerRequestContent content;

    public ServerRequest(ServerRequestContent content) {
        super("### SERVER ###");
        this.content = content;
    }

    public ServerRequestContent getContent() {
        return content;
    }
}
