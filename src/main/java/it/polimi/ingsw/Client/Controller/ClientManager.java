package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.ChoseGodsRequest;
import it.polimi.ingsw.Network.Message.Requests.PickGodRequest;
import it.polimi.ingsw.Network.Message.Requests.PlaceWorkerRequest;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Responses.ShowDeckResponse;
import it.polimi.ingsw.Network.Message.Responses.PickGodResponse;
import it.polimi.ingsw.Network.Message.Responses.PlaceWorkerResponse;
import it.polimi.ingsw.Network.Message.Responses.Response;

import java.io.IOException;
import java.util.ArrayList;

public class ClientManager implements ClientManagerListener {

    private final ClientView clientView;

    private static String username;



    public ClientManager(ClientView clientView){

        this.clientView = clientView;


    }






    public void handleMessageFromServer(Message message){

        clientView.debug((Response) message);

        if(((Response) message).getResponseContent() != null) {
            switch (((Response) message).getResponseContent()){

                case LOGIN:
                    if(message.getMessageStatus() == MessageStatus.OK) return;
                    else login();
                    break;

                case NUM_PLAYER:
                    chooseNumPlayers();
                    break;

                case SHOW_DECK:

                    if(message instanceof ShowDeckResponse)
                        chooseGodFromDeck((ShowDeckResponse) message);
                    else
                        // confirm
                        System.out.println(((Response) message).getGameManagerSays());

                    break;

                case PICK_GOD:

                    if(message instanceof PickGodResponse)
                        pickGod((PickGodResponse) message);
                    else
                        // confirm
                        System.out.println(((Response) message).getGameManagerSays());

                    break;

                case PLACE_WORKER:

                    if(message instanceof PlaceWorkerResponse)
                        placeWorker((PlaceWorkerResponse) message);
                    else
                        // confirm
                        System.out.println(((Response) message).getGameManagerSays());

                    break;

                case START_TURN:
                    break;
                case SELECT_WORKER:
                    break;
                case MOVE_WORKER:
                    break;
                case BUILD:
                    break;
                case END_TURN:
                    break;
                case PLAYER_WON:
                    break;
                default: CHECK:
                    clientView.debug((Response) message);
                    break;
            }
        }

    }





    // functions
    public String askUsername() {

        return clientView.askUserName();

    }
    public void login(){

        username = askUsername();

        try {
            Client.sendMessage(
                    new Request(username, Dispatcher.SETUP_GAME, RequestContent.LOGIN, MessageStatus.OK, username)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void chooseNumPlayers(){

        Integer num = clientView.askNumbOfPlayer();

        try {
            Client.sendMessage(
                    new Request(username, Dispatcher.SETUP_GAME, RequestContent.NUM_PLAYER, MessageStatus.OK, num.toString())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void chooseGodFromDeck(ShowDeckResponse response){

        int howMany = response.getHowMany();
        String serverSays = response.getGameManagerSays();

        ArrayList<God> godChoosen = clientView.selectGodsFromDeck(howMany, serverSays);

        try {

            Client.sendMessage(
                    new ChoseGodsRequest(username, godChoosen)
            );

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void pickGod(PickGodResponse message) {

        ArrayList<God> hand = message.getGods();

        God picked = clientView.pickFromChosenGods(hand);

        try {
            Client.sendMessage(
                    new PickGodRequest(username, picked)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void placeWorker(PlaceWorkerResponse message) {

        Position p = clientView.placeWorker(message.getWorker().toString());

        try {
            Client.sendMessage(
                    new PlaceWorkerRequest(username, message.getWorker(), p)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }





    @Override
    public void update(Response response) { }
}
