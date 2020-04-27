package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

/**
 * The {@link Request} class is needed to split every possible move from the player.
 */
public class Request extends Message {

    private Dispatcher messageDispatcher;
    private String clientManagerSays;

    public Request(String messageSender, Dispatcher messageDispatcher, MessageContent messageContent, MessageStatus messageStatus) {
        super(messageSender, messageContent, messageStatus);
        this.messageDispatcher = messageDispatcher;
    }

    public Request(String messageSender, Dispatcher messageDispatcher, MessageContent messageContent, MessageStatus messageStatus, String clientManagerSays) {
        super(messageSender, messageContent, messageStatus);
        this.messageDispatcher = messageDispatcher;
        this.clientManagerSays = clientManagerSays;
    }


    public Dispatcher getMessageDispatcher() {
        return messageDispatcher;
    }


    public String getClientManagerSays() {
        return clientManagerSays;
    }
}
