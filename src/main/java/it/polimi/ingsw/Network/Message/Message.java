package it.polimi.ingsw.Network.Message;

public abstract class Message {
    private String messageSender;
    private MessageType messageType;


    public Message(String messageSender, MessageType messageType) {
        this.messageSender = messageSender;
        this.messageType = messageType;
    }

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public MessageType getMessageType() {
        return messageType;
    }


}
