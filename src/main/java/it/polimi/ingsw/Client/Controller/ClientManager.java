package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Requests.*;
import it.polimi.ingsw.Network.Message.Responses.*;
import it.polimi.ingsw.Network.Message.Server.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.BuildServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.MoveWorkerServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.SelectWorkerServerRequest;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

import java.io.IOException;
import java.util.ArrayList;

public class ClientManager implements ClientManagerListener {

    private final ClientView clientView;

    // model
    private static String username;
    private God myGod;


    // variabili di controllo
    private Integer workerSelected;
    private boolean myTurn;
    private boolean powerBuildLoop = false;

    ServerRequest serverRequest = null;


    public ClientManager(ClientView clientView){

        this.clientView = clientView;

    }






    public void handleMessageFromServer(Message serverMessage){


        if (serverMessage instanceof ServerRequest) {
            if(!myTurn) { return; }

            serverRequest = (ServerRequest) serverMessage;

            switch (serverRequest.getContent()) {

                case SELECT_WORKER -> {

                    selectWorker();
                    break;
                }

                case MOVE_WORKER -> {

                    moveWorker((MoveWorkerServerRequest) serverRequest);
                    break;
                }

                case BUILD -> {
                    build((BuildServerRequest) serverRequest);
                    break;
                }

                case END_TURN -> {
                    endTurn();
                    break;
                }

            }
            return;
        }



        Response serverResponse = (Response) serverMessage;

        if(serverResponse.getResponseContent() != null) {

            switch (serverResponse.getResponseContent()){

                case LOGIN:
                    if(serverResponse.getMessageStatus() == MessageStatus.OK) return;
                    else login();
                    break;

                case NUM_PLAYER:
                    chooseNumPlayers();
                    break;

                case SHOW_DECK:

                    if(serverResponse instanceof ShowDeckResponse)
                        chooseGodFromDeck((ShowDeckResponse) serverResponse);
                    else
                        // confirm
                        System.out.println(serverResponse.getGameManagerSays());

                    break;

                case PICK_GOD:

                    if(serverResponse instanceof PickGodResponse)
                        pickGod((PickGodResponse) serverResponse);
                    else
                        // confirm
                        System.out.println(serverResponse.getGameManagerSays());

                    break;

                case PLACE_WORKER:

                    if(serverResponse instanceof PlaceWorkerResponse)
                        placeWorker((PlaceWorkerResponse) serverResponse);
                    else
                        // confirm
                        System.out.println(serverResponse.getGameManagerSays());

                    break;

                case START_TURN:

                    System.out.println(serverResponse.getGameManagerSays());
                    this.myTurn = true;

                    break;

                case SELECT_WORKER:

                    MessageStatus status = serverResponse.getMessageStatus();

                    if (status == MessageStatus.ERROR) {
                        System.out.println(serverResponse.getGameManagerSays());
                        System.out.println();
                        selectWorker();
                        break;

                    }

                    System.out.println(serverResponse.getGameManagerSays());

                    break;

                case MOVE_WORKER:

                    if(!myTurn) break;

                    System.out.println(serverResponse.getGameManagerSays());

                    break;

                case MOVE_WORKER_AGAIN:

                    if(!clientView.askMoveAgain()){
                        try {
                            Client.sendMessage(
                                    new EndMoveRequest(username)
                            );
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    moveWorker((MoveWorkerServerRequest) serverRequest);
                    break;


                case BUILD:

                    if(!myTurn) break;

                    System.out.println(serverResponse.getGameManagerSays());

                    break;

                case BUILD_AGAIN:

                    if(!clientView.askBuildAgain()) {
                        try {
                            Client.sendMessage(
                                    new EndTurnRequest(username)
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    build((BuildServerRequest) serverRequest);



                case END_TURN:

                    System.out.println(serverResponse.getGameManagerSays());

                    break;

                case PLAYER_WON:

                    if(serverResponse instanceof WonResponse)
                        win((WonResponse) serverResponse);
                    else
                        // confirm
                        System.out.println(serverResponse.getGameManagerSays());

                    break;
                default: CHECK:
                    clientView.debug(serverResponse);
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

        this.myGod = clientView.pickFromChosenGods(hand);

        try {
            Client.sendMessage(
                    new PickGodRequest(username, this.myGod)
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

    private void selectWorker(){

        this.workerSelected = clientView.selectWorker();
        try {
            Client.sendMessage(
                    new SelectWorkerRequest(username, this.workerSelected)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void moveWorker(MoveWorkerServerRequest response){

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

    private void build(BuildServerRequest serverRequest){

        ArrayList<Position> nearlyPositionsValid = serverRequest.getPossiblePlaceToBuildOn();

        Position position = clientView.build(nearlyPositionsValid);

        try {
            Client.sendMessage(
                    new BuildRequest(username, position)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void endTurn() {
        this.myTurn = false;
        clientView.endTurn();

        try {
            Client.sendMessage(
                    new EndTurnRequest(username)
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
