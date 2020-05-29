package it.polimi.ingsw.Network.Message.Server;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Client.Controller.Updater;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Message;

public abstract class ServerMessage extends Message implements Updater {

    private ClientManager clientManager;
    private String messageRecipient;

    public ServerMessage() {
        super("### SERVER ###");
    }

    public ServerMessage(MessageStatus messageStatus) {
        super("### SERVER ###", messageStatus);
    }

    public void setMessageRecipient(String messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public void update() {
        execute();
    }

    protected ClientManager getClientManager() {
        return clientManager;
    }

    public String getMessageRecipient() {
        return messageRecipient;
    }
}
