package it.polimi.ingsw.Network.Message.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Requests.Request;

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
