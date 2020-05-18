package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.Model.ClientMap;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Network.Message.Server.Responses.*;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.BuildServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.MoveWorkerServerRequest;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

import java.io.IOException;
import java.util.ArrayList;

public class ClientManager {

    private final ClientView clientView;

    // model
    private Player me;
    private ClientMap clientMap;

    // variabili di controllo
    private Integer workerSelected;
    private boolean myTurn;
    private boolean canMoveAgain = false;
    private boolean canBuildAgain = false;

    //la richiesta corrente che il client sta gestendo
    ServerRequest serverRequest = null;


    public ClientManager(ClientView clientView){

        this.clientView = clientView;

    }






    public void handleMessageFromServer(Message serverMessage){


        //se è il server a chiedere al client di compiere una determinata azione
        // allora riceverà una ServerRequest
        if (serverMessage instanceof ServerRequest) {

            serverRequest = (ServerRequest) serverMessage;

            switch (serverRequest.getContent()) {
                case SELECT_WORKER -> selectWorker();

                case MOVE_WORKER -> {

                    if (canMoveAgain && !clientView.wantMoveAgain()) {
                        canMoveAgain = false;
                        sendEndMoveRequest();
                        break;
                    }

                    //TODO: da controllare sul client se ha la possibilità di fare una PowerButtonRequest
                    moveWorker((MoveWorkerServerRequest) serverRequest);
                }

                case BUILD -> {

                    if (canBuildAgain && !clientView.wantBuildAgain()) {
                        sendEndBuildRequest();
                        break;
                    }

                    build((BuildServerRequest) serverRequest);
                }


                case END_TURN -> endTurn();
            }

            return;
        }



        Response serverResponse = (Response) serverMessage;
        MessageStatus responseStatus = serverResponse.getResponseStatus();

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

                    Response selectWorkerResponse = (SelectWorkerResponse) serverResponse;

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.errorWhileSelectingWorker(selectWorkerResponse.getGameManagerSays());
                        selectWorker();
                        break;
                    }

                    clientView.workerSelectedSuccessfully();

                    break;

                case MOVE_WORKER:

                    MoveWorkerResponse moveWorkerResponse = (MoveWorkerResponse) serverResponse;

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.errorWhileMovingWorker(moveWorkerResponse.getGameManagerSays());
                        moveWorker((MoveWorkerServerRequest) serverRequest);
                        break;
                    }

                    this.canMoveAgain = false;
                    clientView.workerMovedSuccessfully();

                    break;

                case MOVE_WORKER_AGAIN:

                    this.canMoveAgain = true;
                    clientView.printCanMoveAgain(serverResponse.getGameManagerSays());

                    break;

                case END_MOVE:

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.endMoveRequestError(serverResponse.getGameManagerSays());
                        break;
                    }

                    clientView.endMovingPhase(serverResponse.getGameManagerSays());

                    break;

                case BUILD:

                    BuildResponse buildResponse = (BuildResponse) serverResponse;

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.errorWhileBuilding(buildResponse.getGameManagerSays());
                        build((BuildServerRequest) serverRequest);
                        break;
                    }

                    this.canBuildAgain = false;
                    clientView.builtSuccessfully();

                    break;

                case BUILD_AGAIN:

                    this.canBuildAgain = true;
                    clientView.printCanBuildAgain(serverResponse.getGameManagerSays());

                    break;

                case END_BUILD:

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.endBuildRequestError(serverResponse.getGameManagerSays());
                        break;
                    }

                    clientView.endBuildingPhase(serverResponse.getGameManagerSays());

                    break;


                case END_TURN:

                    //clientView.endingTurn();

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

    private void sendEndMoveRequest() {
        try {
            Client.sendMessage(
                    new EndMoveRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendEndBuildRequest() {
        try {
            Client.sendMessage(
                    new EndBuildRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    // functions
    private String askUsername() {

        return clientView.askUserName();

    }






    public void login(){

        me = new Player(askUsername());

        try {
            Client.sendMessage(
                    new Request(me.getPlayerName(), Dispatcher.SETUP_GAME, RequestContent.LOGIN, MessageStatus.OK, me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void chooseNumPlayers(){

        Integer num = clientView.askNumbOfPlayer();

        try {
            Client.sendMessage(
                    new Request(me.getPlayerName(), Dispatcher.SETUP_GAME, RequestContent.NUM_PLAYER, MessageStatus.OK, num.toString())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void chooseGodFromDeck(ShowDeckResponse response){

        int howMany = response.getHowMany();

        clientView.showDeck();

        ArrayList<God> godChoosen = clientView.selectGods(howMany);

        try {

            Client.sendMessage(
                    new ChoseGodsRequest(me.getPlayerName(), godChoosen)
            );

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void pickGod(PickGodResponse message) {

        ArrayList<God> hand = message.getGods();

        God myGod = clientView.pickFromChosenGods(hand);
        me.setPlayerGod(myGod);

        try {
            Client.sendMessage(
                    new PickGodRequest(me.getPlayerName(), myGod)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void placeWorker(PlaceWorkerResponse message) {

        Position p = clientView.placeWorker(message.getWorker().toString());

        try {
            Client.sendMessage(
                    new PlaceWorkerRequest(me.getPlayerName(), message.getWorker(), p)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void selectWorker(){

        this.workerSelected = clientView.selectWorker();

        try {
            Client.sendMessage(
                    new SelectWorkerRequest(me.getPlayerName(), this.workerSelected)
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
                    new MoveRequest(me.getPlayerName(), position)
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
                    new BuildRequest(me.getPlayerName(), position)
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
                    new EndTurnRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void win(WonResponse response){
        clientView.win(response.getGameManagerSays().equals(me.getPlayerName()));
    }


}
