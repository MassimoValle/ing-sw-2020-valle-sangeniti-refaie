package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Requests.*;
import it.polimi.ingsw.Network.Message.Responses.*;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

import java.io.IOException;
import java.util.ArrayList;

public class ClientManager implements ClientManagerListener {

    private final ClientView clientView;

    private static String username;
    private God god;

    private Integer workerSelected;



    public ClientManager(ClientView clientView){

        this.clientView = clientView;

    }






    public void handleMessageFromServer(Response message){

        clientView.debug(message);

        if(message.getResponseContent() != null) {

            switch (message.getResponseContent()){

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
                        System.out.println(message.getGameManagerSays());

                    break;

                case PICK_GOD:

                    if(message instanceof PickGodResponse)
                        pickGod((PickGodResponse) message);
                    else
                        // confirm
                        System.out.println(message.getGameManagerSays());

                    break;

                case PLACE_WORKER:

                    if(message instanceof PlaceWorkerResponse)
                        placeWorker((PlaceWorkerResponse) message);
                    else
                        // confirm
                        System.out.println(message.getGameManagerSays());

                    break;

                case START_TURN:
                    break;

                case SELECT_WORKER:

                    if(message instanceof SelectWorkerResponse)
                        selectWorker((SelectWorkerResponse) message);
                    else
                        // confirm
                        System.out.println(message.getGameManagerSays());

                    break;

                case MOVE_WORKER:

                    if(message instanceof MoveResponse)
                        moveWorker((MoveResponse) message);
                    else
                        // confirm
                        System.out.println(message.getGameManagerSays());

                    break;

                case BUILD:

                    if(message instanceof BuildResponse)
                        build((BuildResponse) message);
                    else
                        // confirm
                        System.out.println(message.getGameManagerSays());

                    break;

                case END_TURN:
                    break;

                case PLAYER_WON:

                    if(message instanceof WonResponse)
                        win((WonResponse) message);
                    else
                        // confirm
                        System.out.println(message.getGameManagerSays());

                    break;
                default: CHECK:
                    clientView.debug(message);
                    break;
            }
        }

    }





    // functions
    private String askUsername() {

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
        this.god = picked;

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

    private void selectWorker(SelectWorkerResponse respose){

        workerSelected = clientView.selectWorker();
        try {
            Client.sendMessage(
                    new SelectWorkerRequest(username, workerSelected)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void moveWorker(MoveResponse response){

        ArrayList<Position> nearlyPositionsValid = response.getNearlyPositions();

        Position position = clientView.moveWorker(nearlyPositionsValid);

        try {
            Client.sendMessage(
                    new MoveRequest(username, position)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void build(BuildResponse response){

        ArrayList<Position> nearlyPositionsValid = response.getPossiblePosToBuild();

        Position position = clientView.build(nearlyPositionsValid);

        try {
            Client.sendMessage(
                    new BuildRequest(username, position)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void win(WonResponse response){
        clientView.win(response.getGameManagerSays().equals(username));
    }





    @Override
    public void update(Response response) { }
}
