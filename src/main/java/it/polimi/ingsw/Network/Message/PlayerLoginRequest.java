package it.polimi.ingsw.Network.Message;

public class PlayerLoginRequest extends Request{

    public PlayerLoginRequest(String username) {
        super(username, MessageContent.LOGIN, username);
    }

    @Override
    public String toString() {
        return "Username " + super.getMessageSender() +
                "\nText inserted: " + super.getUserInput();
    }
}
