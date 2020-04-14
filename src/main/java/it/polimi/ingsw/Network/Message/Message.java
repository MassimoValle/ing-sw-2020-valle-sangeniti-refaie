package it.polimi.ingsw.Network.Message;

public abstract class Message {
    private String messageSender;
    private MessageContent messageContent;


    public Message(String messageSender, MessageContent messageContent) {
        this.messageSender = messageSender;
        this.messageContent = messageContent;
    }

    public Message(MessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public MessageContent getMessageContent() {
        return messageContent;
    }


}
