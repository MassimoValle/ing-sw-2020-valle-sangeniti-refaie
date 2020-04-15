package it.polimi.ingsw.Network.Message;

public class Response extends Message {

    private final String message;
    private final MessageStatus status;


    public Response(String message, MessageStatus messageStatus) {
        super("[SERVER]", MessageContent.CONNECTION_RESPONSE);
        this.message = message;
        this.status = messageStatus;
    }

    public String getMessage() {
        return  message;
    }

    public MessageStatus getMessageStatus() {
        return status;
    }

}
