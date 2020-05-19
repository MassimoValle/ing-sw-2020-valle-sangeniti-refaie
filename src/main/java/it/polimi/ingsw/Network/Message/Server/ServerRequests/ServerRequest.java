package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Server.ServerMessage;

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
    public void updateClient(ClientManager clientManager) {
        clientManager.handleServerRequest(this);
    }
}
