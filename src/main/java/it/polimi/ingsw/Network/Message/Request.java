package it.polimi.ingsw.Network.Message;

public class Request extends Message {

    private String userInput;

    public Request(String username, MessageContent messageContent, String userInput) {
        super(username, messageContent);

        this.userInput = userInput;
    }

    public String getUserInput() {
        return userInput;
    }

    @Override
    public String toString() {
        return "Username " + super.getMessageSender() +
                "\nMessageContent: " + getMessageContent() +
                "\nText inserted: " + getUserInput();
    }
}
