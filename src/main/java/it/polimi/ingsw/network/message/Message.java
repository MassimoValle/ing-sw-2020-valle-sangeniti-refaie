package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.message.Enum.MessageStatus;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final String messageSender;
    private MessageStatus messageStatus;

    public Message(String messageSender, MessageStatus messageStatus) {
        this.messageSender = messageSender;
        this.messageStatus = messageStatus;
    }

    public Message(String messageSender) {
        this.messageSender = messageSender;
    }




    public String getMessageSender() { return messageSender; }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    @Override
    public String toString() {
        return "\nMessageSender: " + messageSender +
                "\nMessageStatus: " + getMessageStatus();
    }



}
