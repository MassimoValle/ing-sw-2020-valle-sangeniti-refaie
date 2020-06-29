package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Enum.MessageStatus;

/**
 * The {@link Request} class is needed to split every possible move from the player.
 */
public class Request extends Message {

    private final Dispatcher messageDispatcher;
    private RequestContent requestContent;

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


    public Dispatcher getMessageDispatcher() {
        return messageDispatcher;
    }


    public RequestContent getRequestContent() {
        return requestContent;
    }
}
