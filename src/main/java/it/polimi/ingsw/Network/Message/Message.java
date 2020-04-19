package it.polimi.ingsw.Network.Message;

import java.io.Serializable;

public class Message implements Serializable {
    private String messageSender;
    private MessageContent messageContent;
    private MessageStatus messageStatus;
    private String message;


    public Message(String messageSender, String message) {
        this.messageSender = messageSender;
        this.message = message;
    }

    public Message(String messageSender, MessageContent messageContent, String message) {
        this(messageSender, message);
        this.messageContent = messageContent;
    }

    public Message(String messageSender, MessageStatus messageStatus, String message) {
        this(messageSender, message);
        this.messageStatus = messageStatus;
    }

    public Message(String messageSender, MessageContent messageContent, MessageStatus messageStatus, String message) {
        this(messageSender, messageContent, message);
        this.messageStatus = messageStatus;
    }


    public String getMessageSender() { return messageSender; }

    public MessageContent getMessageContent() { return messageContent; }

    public MessageStatus getMessageStatus() { return messageStatus; }

    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "\nMessageSender: " + messageSender +
                "\nMessageContent: " + messageContent +
                "\nMessageStatus: " + messageStatus +
                "\nmessage:" + message;
    }
}
