package it.polimi.ingsw.Network.Message;

public class Response extends Message {

    private final String message;
    private final MessageStatus status;


    public Response(String message, MessageStatus status) {
        super("---", MessageType.CONNECTION_RESPONSE);
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return  message;
    }

    public MessageStatus getMessageStatus() {
        return status;
    }

}
