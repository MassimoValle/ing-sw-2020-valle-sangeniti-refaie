package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Network.Message.Server.Responses.*;
import it.polimi.ingsw.Network.Message.Server.ServerMessage;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.BuildServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.MoveWorkerServerRequest;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdatePlayersMessage;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ClientManager {

    public static ClientView clientView = null;

    // model
    private Player me;
    private final BabyGame babyGame;

    private final ClientBoardUpdater clientBoardUpdater;

    // variabili di controllo
    private Integer workerSelected;
    private boolean myTurn;
    private boolean canMoveAgain = false;
    private boolean canBuildAgain = false;

    //la richiesta corrente che il client sta gestendo
    ServerRequest serverRequest = null;


    public ClientManager(ClientView clientView){

        this.clientView = clientView;

        this.clientBoardUpdater = new ClientBoardUpdater();
        babyGame = BabyGame.getInstance();

    }






    public void handleMessageFromServer(ServerMessage serverMessage){

        if (!myTurn) {
            clientView.someoneElseDoingStuff();
        }

        serverMessage.updateClient(this);

        clientView.showMap(getGameMap());

    }

    /**
     * Update {@link Player} 'me' with the real info
     */
    private void updateMyInfo() {
        if (babyGame.getPlayerByName(me.getPlayerName()) != null)
            me = babyGame.getPlayerByName(me.getPlayerName());
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

    public void handleServerRequest(ServerRequest serverRequest) {

        switch (serverRequest.getContent()) {
            case SELECT_WORKER -> selectWorker();

            case MOVE_WORKER -> {

                if (canMoveAgain && !clientView.wantMoveAgain()) {
                    //canMoveAgain = false;
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

    }

    public void handleServerResponse(ServerResponse serverResponse) {
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

                    if(serverResponse instanceof ShowDeckServerResponse)
                        chooseGodFromDeck((ShowDeckServerResponse) serverResponse);
                    else
                        // confirm
                        //System.out.println(serverResponse.getGameManagerSays());

                        break;

                case PICK_GOD:

                    if(serverResponse instanceof PickGodServerResponse)
                        pickGod((PickGodServerResponse) serverResponse);
                    else
                        // confirm
                        //System.out.println(serverResponse.getGameManagerSays());

                        break;

                case PLACE_WORKER:

                    if(serverResponse instanceof PlaceWorkerServerResponse)
                        placeWorker((PlaceWorkerServerResponse) serverResponse);
                    else
                        // confirm
                        //clientView.workerPlacedSuccesfully(serverResponse.getGameManagerSays());

                        break;

                case START_TURN:

                    clientView.startingTurn(serverResponse.getGameManagerSays());
                    this.myTurn = true;

                    break;

                case SELECT_WORKER:

                    assert serverResponse instanceof SelectWorkerServerResponse;
                    SelectWorkerServerResponse selectWorkerResponse = (SelectWorkerServerResponse) serverResponse;

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.errorWhileSelectingWorker(selectWorkerResponse.getGameManagerSays());
                        selectWorker();
                        break;
                    }

                    clientView.workerSelectedSuccessfully();

                    break;

                case MOVE_WORKER:

                    assert serverResponse instanceof MoveWorkerServerResponse;
                    MoveWorkerServerResponse moveWorkerResponse = (MoveWorkerServerResponse) serverResponse;

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.errorWhileMovingWorker(moveWorkerResponse.getGameManagerSays());
                        moveWorker((MoveWorkerServerRequest) serverRequest);
                        break;
                    }

                    this.canMoveAgain = false;
                    clientView.workerMovedSuccessfully();

                    break;

                case MOVE_WORKER_AGAIN:

                    clientView.workerMovedSuccessfully();

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

                    BuildServerResponse buildResponse = (BuildServerResponse) serverResponse;

                    if (responseStatus == MessageStatus.ERROR) {
                        clientView.errorWhileBuilding(buildResponse.getGameManagerSays());
                        build((BuildServerRequest) serverRequest);
                        break;
                    }

                    this.canBuildAgain = false;
                    clientView.builtSuccessfully();

                    break;

                case BUILD_AGAIN:

                    clientView.builtSuccessfully();

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

                    if(serverResponse instanceof WonServerResponse)
                        win((WonServerResponse) serverResponse);
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

    private void chooseGodFromDeck(ShowDeckServerResponse response){

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

    private void pickGod(PickGodServerResponse message) {

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

    private void placeWorker(PlaceWorkerServerResponse message) {

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

        ArrayList<Position> nearlyPositionsValid = me.getPlayerWorkers().get(workerSelected).getWorkerPosition().getAdjacentPlaces();

        Position position = clientView.moveWorker(nearlyPositionsValid);

        try {
            Client.sendMessage(
                    new MoveRequest(me.getPlayerName(), position)
            );
        }catch (IOException e){
            e.printStackTrace();
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

    private void sendEndBuildRequest() {
        try {
            Client.sendMessage(
                    new EndBuildRequest(me.getPlayerName())
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

    private void win(WonServerResponse response){
        clientView.win(response.getGameManagerSays().equals(me.getPlayerName()));
    }

    private GameMap getGameMap() {
        return babyGame.clientMap;
    }

    public void updatePlayerInfo(UpdatePlayersMessage updatePlayersMessage) {
        babyGame.addPlayers(updatePlayersMessage);
        Set<Player> players = babyGame.players;
        clientView.showAllPlayersInGame(players);
        updateMyInfo();
    }

    public ClientBoardUpdater getClientBoardUpdater() {
        return this.clientBoardUpdater;
    }
}
