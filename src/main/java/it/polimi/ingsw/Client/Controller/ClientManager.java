package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.*;
import it.polimi.ingsw.Network.Message.Server.ServerMessage;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.*;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.*;
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
    ServerRequest currentServerRequest = null;

    //Qualsiasi messaggio che il client riceve dal server
    ServerMessage currentMessage = null;


    public ClientManager(ClientView clientView){

        this.clientView = clientView;

        this.clientBoardUpdater = new ClientBoardUpdater();
        babyGame = BabyGame.getInstance();

    }



    public void handleMessageFromServer(ServerMessage serverMessage){

        takeMessage(serverMessage);

    }

    //mette il messaggio in arrivo in current message e
    // setta il client sul messaggio per poter fare l'azione desiderata sul client
    private void takeMessage(ServerMessage updateServerMessage) {
        this.currentMessage = updateServerMessage;
        currentMessage.setClientManager(this);
        currentMessage.update();

    }

    public void handleServerRequest(ServerRequest serverRequest) {
        currentServerRequest = serverRequest;

        switch (serverRequest.getContent()) {

            case CHOOSE_GODS_SERVER_REQUEST -> chooseGodFromDeck((ChooseGodsServerRequest) serverRequest);

            case PICK_GOD -> pickGod((PickGodServerRequest) serverRequest);

            case PLACE_WORKER -> placeWorker((PlaceWorkerServerRequest) serverRequest);

            case START_TURN -> startTurn();

            case SELECT_WORKER -> selectWorker();

            case MOVE_WORKER -> handleMoveWorkerServerRequest((MoveWorkerServerRequest) serverRequest);

            case BUILD -> handleBuildServerRequest((BuildServerRequest) serverRequest);

            case END_TURN -> endTurn();
        }

    }

    public void handleServerResponse(ServerResponse serverResponse) {
        MessageStatus responseStatus = serverResponse.getResponseStatus();

        if(serverResponse.getResponseContent() != null) {

            switch (serverResponse.getResponseContent()){
                case LOGIN -> {
                    if(serverResponse.getMessageStatus() == MessageStatus.OK) return;
                    else login();
                }

                case NUM_PLAYER -> chooseNumPlayers();

                case CHOOSE_GODS -> handleChooseGodsServerResponse((ChooseGodsServerResponse) serverResponse);
                case PICK_GOD -> handlePickGodServerResponse((PickGodServerResponse) serverResponse);
                case PLACE_WORKER -> handlePlaceWorkerServerResponse((PlaceWorkerServerResponse) serverResponse);

                case SELECT_WORKER -> handleSelectWorkerServerResponse((SelectWorkerServerResponse) serverResponse);

                case MOVE_WORKER, MOVE_WORKER_AGAIN -> handleMoveWorkerServerResponse((MoveWorkerServerResponse) serverResponse);
                case END_MOVE -> handleEndMoveServerResponse((EndMoveServerResponse) serverResponse);

                case BUILD, BUILD_AGAIN -> handleBuildServerResponse((BuildServerResponse) serverResponse);
                case END_BUILD ->handleEndBuildServerResponse((EndBuildServerResponse) serverResponse);

                case PLAYER_WON -> playerWon((WonServerResponse) serverResponse);
            }
        }
    }



    // functions
    public void login(){

        me = new Player(askUsername());

        try {
            Client.sendMessage(
                    new LoginRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private String askUsername() {

        return clientView.askUserName();

    }

    private void chooseNumPlayers(){

        Integer num = clientView.askNumbOfPlayer();

        try {
            Client.sendMessage(
                    new SetPlayersRequest(me.getPlayerName(), Dispatcher.SETUP_GAME, RequestContent.NUM_PLAYER, num.toString())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void chooseGodFromDeck(ChooseGodsServerRequest serverRequest){

        int howMany = serverRequest.getHowMany();

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

    private void pickGod(PickGodServerRequest serverRequest) {

        ArrayList<God> hand = (ArrayList<God>) serverRequest.getGods();

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

    private void placeWorker(PlaceWorkerServerRequest serverRequest) {

        Position p = clientView.placeWorker(serverRequest.getWorker().toString());

        try {
            Client.sendMessage(
                    new PlaceWorkerRequest(me.getPlayerName(), serverRequest.getWorker(), p)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void startTurn() {
        clientView.startingTurn();
        this.myTurn = true;
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

        Position myWorkerPosition = me.getPlayerWorkers().get(workerSelected).getWorkerPosition();

        ArrayList<Position> nearlyPositionsValid = myWorkerPosition.getAdjacentPlaces();

        nearlyPositionsValid.add(myWorkerPosition);

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

    private void playerWon(WonServerResponse response){
        clientView.win(response.getGameManagerSays().equals(me.getPlayerName()));
    }



    //handler dei messaggi, utilizzare una classe apposita(???)
    private void handleChooseGodsServerResponse(ChooseGodsServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhileChoosingGods(serverResponse.getGameManagerSays());
            chooseGodFromDeck((ChooseGodsServerRequest) currentServerRequest);
            return;
        }

        clientView.godsSelectedSuccesfully();

    }

    private void handlePickGodServerResponse(PickGodServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhilePickinUpGod(serverResponse.getGameManagerSays());
            pickGod((PickGodServerRequest) currentServerRequest);
            return;
        }
        clientView.godPickedUpSuccessfully();
    }

    private void handlePlaceWorkerServerResponse(PlaceWorkerServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhilePlacingYourWorker(serverResponse.getGameManagerSays());
            placeWorker((PlaceWorkerServerRequest) currentServerRequest);
            return;
        }

        clientView.workerPlacedSuccesfully();

    }

    private void handleSelectWorkerServerResponse(SelectWorkerServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhileSelectingWorker(serverResponse.getGameManagerSays());
            selectWorker();
            return;
        }

        clientView.workerSelectedSuccessfully();
    }

    private void handleMoveWorkerServerRequest(MoveWorkerServerRequest serverRequest) {
        if (canMoveAgain && !clientView.wantMoveAgain()) {
            sendEndMoveRequest();
            return;
        }

        //TODO: da controllare sul client se ha la possibilità di fare una PowerButtonRequest
        moveWorker(serverRequest);
    }

    private void handleMoveWorkerServerResponse(MoveWorkerServerResponse serverResponse) {

        switch (serverResponse.getResponseContent()) {

            case MOVE_WORKER -> {

                if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
                    clientView.errorWhileMovingWorker(serverResponse.getGameManagerSays());
                    moveWorker((MoveWorkerServerRequest) currentServerRequest);
                    break;
                }

                this.canMoveAgain = false;
                clientView.workerMovedSuccessfully();

            }

            case MOVE_WORKER_AGAIN -> {

                clientView.workerMovedSuccessfully();
                this.canMoveAgain = true;
                clientView.printCanMoveAgain(serverResponse.getGameManagerSays());

            }
        }
    }

    private void handleBuildServerRequest(BuildServerRequest serverRequest) {

        if (canBuildAgain && !clientView.wantBuildAgain()) {
            sendEndBuildRequest();
            return;
        }

        build(serverRequest);

    }

    private void handleBuildServerResponse(BuildServerResponse serverResponse) {

        switch (serverResponse.getResponseContent()) {

            case BUILD -> {

                if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
                    clientView.errorWhileBuilding(serverResponse.getGameManagerSays());
                    build((BuildServerRequest) currentServerRequest);
                    break;
                }

                this.canBuildAgain = false;
                clientView.builtSuccessfully();
            }

            case BUILD_AGAIN -> {

                clientView.builtSuccessfully();
                this.canBuildAgain = true;
                clientView.printCanBuildAgain(serverResponse.getGameManagerSays());
            }

        }
    }

    private void handleEndMoveServerResponse(EndMoveServerResponse serverResponse) {
        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.endMoveRequestError(serverResponse.getGameManagerSays());
            return;
        }
        clientView.endMovingPhase(serverResponse.getGameManagerSays());
    }

    private void handleEndBuildServerResponse(EndBuildServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.endBuildRequestError(serverResponse.getGameManagerSays());
            return;
        }
        clientView.endBuildingPhase(serverResponse.getGameManagerSays());
    }

    public void updatePlayerInfo(UpdatePlayersMessage updatePlayersMessage) {
        babyGame.addPlayers(updatePlayersMessage);
        Set<Player> players = babyGame.players;
        clientView.showAllPlayersInGame(players);
        updateMyInfo();
    }

    /**
     * Update {@link Player} 'me' with the real info
     */
    private void updateMyInfo() {
        if (babyGame.getPlayerByName(me.getPlayerName()) != null)
            me = babyGame.getPlayerByName(me.getPlayerName());
    }

    public void boardUpdate(UpdateBoardMessage updateBoardMessage) {
        clientBoardUpdater.boardUpdate(updateBoardMessage);
        clientView.showMap(getGameMap());
    }

    private GameMap getGameMap() {
        return babyGame.clientMap;
    }
}
