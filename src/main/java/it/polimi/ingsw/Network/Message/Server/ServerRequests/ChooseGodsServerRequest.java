package it.polimi.ingsw.Network.Message.Server.ServerRequests;


import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;

public class ChooseGodsServerRequest extends ServerRequest {

    private final int godToChoose;

    public ChooseGodsServerRequest(int howMany) {
        super(ServerRequestContent.CHOOSE_GODS_SERVER_REQUEST);
        this.godToChoose = howMany;
    }

    public int getHowMany() {
        return godToChoose;
    }

}
