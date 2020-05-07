package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

/**
 * The {@link Request} class is needed to split every possible move from the player.
 */
public class Request extends Message {

    private final Dispatcher messageDispatcher;
    private RequestContent requestContent;
    private String clientManagerSays;

    public Request(String messageSender, Dispatcher messageDispatcher, RequestContent requestContent, MessageStatus messageStatus) {
        super(messageSender, messageStatus);
        this.requestContent = requestContent;
        this.messageDispatcher = messageDispatcher;
    }

    public Request(String messageSender, Dispatcher messageDispatcher, RequestContent requestContent) {
        super(messageSender);
        this.requestContent = requestContent;
        this.messageDispatcher = messageDispatcher;
    }



    public Request(String messageSender, Dispatcher messageDispatcher, RequestContent requestContent, MessageStatus messageStatus, String clientManagerSays) {
        super(messageSender, messageStatus);
        this.requestContent = requestContent;
        this.messageDispatcher = messageDispatcher;
        this.clientManagerSays = clientManagerSays;
    }


    public Dispatcher getMessageDispatcher() {
        return messageDispatcher;
    }


    public String getClientManagerSays() {
        return clientManagerSays;
    }

    public RequestContent getRequestContent() {
        return requestContent;
    }
}
