package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.PossibleClientAction;
import it.polimi.ingsw.network.message.server.serverresponse.SelectWorkerServerResponse;
import it.polimi.ingsw.network.message.server.serverresponse.ServerResponse;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ClientInterface {

    String askIpAddress();

    String askUserName();

    int askNumbOfPlayer();

    void youAreNotTheGodLikePlayer(String godLikePlayer);

    void youAreTheGodLikePlayer();

    void showDeck();

    ArrayList<God> selectGods(int howMany);

    void errorWhileChoosingGods(String gameManagerSays);

    void godsSelectedSuccesfully();

    God pickFromChosenGods(ArrayList<God> hand);

    void errorWhilePickinUpGod(String gameManagerSays);

    void godPickedUpSuccessfully();

    void showAllPlayersInGame(Set<Player> playerSet, Player me);

    Position placeWorker(String worker);

    void errorWhilePlacingYourWorker(String gameManagerSays);

    void workerPlacedSuccesfully();

    void startingTurn();

    int selectWorker(List<Position> workersPositions);

    void errorWhileSelectingWorker(String gameManagerSays);

    void workerSelectedSuccessfully();

    PossibleClientAction choseActionToPerform(List<PossibleClientAction> possibleActions);

    void errorWhileActivatingPower(String gameManagerSays);

    void powerActivated(God god);

    Position moveWorker(List<Position> nearlyPosValid);

    void errorWhileMovingWorker(String gameManagerSays);

    /**
     * Ask the player if he wants to move again
     *
     * @return the boolean
     */
    boolean wantMoveAgain();

    void printCanMoveAgain(String gameManagaerSays);

    void workerMovedSuccessfully();

    void endMoveRequestError(String gameManagerSays);

    void endMovingPhase(String gameManagerSays);

    Position build(ArrayList<Position> possiblePosToBuild);

    boolean wantBuildAgain();

    void printCanBuildAgain(String gameManagerSays);

    void errorWhileBuilding(String gameManagerSays);

    void builtSuccessfully();

    void endBuildRequestError(String gameManagerSays);

    void endBuildingPhase(String gameManagerSays);

    void endTurn();

    void someoneElseDoingStuff();

    void anotherPlayerIsPickingUpGod(String turnOwner);
    void anotherPlayerIsPlacingWorker(String turnOwner);
    void startingPlayerTurn(String turnOwner);
    void anotherPlayerIsSelectingWorker(String turnOwner);
    void anotherPlayerIsMoving(String turnOwner);
    void anotherPlayerIsBuilding(String turnOwner);

    void anotherPlayerHasSelectedGods(String turnOwner);
    void anotherPlayerHasPickedUpGod(String turnOwner);
    void anotherPlayerHasPlacedWorker(String turnOwner);
    void anotherPlayerHasSelectedWorker(SelectWorkerServerResponse serverResponse);
    void anotherPlayerHasMoved(String turnOwner);
    void anotherPlayerHasBuilt(String turnOwner);
    void anotherPlayerHasEndedHisTurn(String turnOwner);

    void youWon();
    void iLost();
    void someoneHasLost(String loser);

    void playerLeftTheGame(String user);

    void youLose(String winner);

    void debug(ServerResponse serverResponse);

    void showMap(GameMap clientMap);
}
