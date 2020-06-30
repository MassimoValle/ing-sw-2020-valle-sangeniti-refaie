package it.polimi.ingsw.network.message.server;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Updater;
import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Message;

public abstract class ServerMessage extends Message implements Updater {

    private transient ClientManager clientManager;
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
