package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.PossibleClientAction;
import it.polimi.ingsw.network.message.Server.ServerResponse.SelectWorkerServerResponse;
import it.polimi.ingsw.network.message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.server.model.God.God;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Position;

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

    void showAllPlayersInGame(Set<Player> playerSet);

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

    void doNothing();

    void youWon();
    void iLost();
    void someoneHasLost(String loser);

    void youLose(String winner);

    void debug(ServerResponse serverResponse);

    void showMap(GameMap clientMap);
}
