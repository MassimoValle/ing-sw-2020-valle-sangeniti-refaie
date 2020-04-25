package it.polimi.ingsw.Network.Message;

import java.io.Serializable;

public class Message implements Serializable {
    private final String messageSender;
    private MessageContent messageContent;
    private MessageStatus messageStatus;

    public Message(String messageSender, MessageContent messageContent, MessageStatus messageStatus) {
        this.messageSender = messageSender;
        this.messageContent = messageContent;
        this.messageStatus = messageStatus;
    }


    public String getMessageSender() { return messageSender; }

    public MessageContent getMessageContent() { return messageContent; }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    @Override
    public String toString() {
        return "\nMessageSender: " + messageSender +
                "\nMessageContent: " + messageContent +
                "\nMessageStatus: " + getMessageStatus();
    }



}
