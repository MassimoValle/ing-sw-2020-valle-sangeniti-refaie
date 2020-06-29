package it.polimi.ingsw.network.message.Server.ServerRequests;


import it.polimi.ingsw.network.message.Enum.ServerRequestContent;

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
