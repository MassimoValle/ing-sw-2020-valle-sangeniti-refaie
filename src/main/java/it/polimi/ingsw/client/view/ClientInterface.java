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

/**
 * The interface Client interface.
 */
public interface ClientInterface {

    /**
     * Ask ip address string to the user.
     *
     * @return the string contains the ip address of server
     */
    String askIpAddress();

    /**
     * Ask username string.
     *
     * @return the string contains the username of the user
     */
    String askUserName();

    void loginError();

    void loginSuccessful();

    /**
     * Ask numb of player int.
     *
     * @return the lobby size to set on server
     */
    int askNumbOfPlayer();

    /**
     * You are not the god like player. (godLikePlayer is who choose the gods from deck)
     * Print the name of the godLikePlayer on screen
     *
     * @param godLikePlayer contains the name of the godLikePlayer
     */
    void youAreNotTheGodLikePlayer(String godLikePlayer);

    /**
     * You are the god like player.
     */
    void youAreTheGodLikePlayer();

    /**
     * Show deck.
     */
    void showDeck();

    /**
     * Select gods from deck.
     *
     * @param howMany the how many gods to select from deck
     * @return the array list return gods chosen
     */
    ArrayList<God> selectGods(int howMany);

    /**
     * Error while choosing gods.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhileChoosingGods(String gameManagerSays);

    /**
     * Gods selected successfully.
     */
    void godsSelectedSuccessfully();

    /**
     * Pick from chosen gods for assign a god to myself
     *
     * @param hand contains the gods chosen from godLikePlayer
     * @return the god who I choose
     */
    God pickFromChosenGods(ArrayList<God> hand);

    /**
     * Error while picking up god.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhilePickingUpGod(String gameManagerSays);

    /**
     * God picked up successfully.
     */
    void godPickedUpSuccessfully();

    /**
     * Show all players in game.
     *
     * @param playerSet the player set
     * @param me        the me
     */
    void showAllPlayersInGame(Set<Player> playerSet, Player me);

    /**
     * Place worker position.
     *
     * @param worker the worker to place
     * @return the position where I place my worker
     */
    Position placeWorker(String worker);

    /**
     * Error while placing your worker.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhilePlacingYourWorker(String gameManagerSays);

    /**
     * Worker placed successfully.
     */
    void workerPlacedSuccessfully();

    /**
     * Starting turn print on screen that is your turn.
     */
    void startingTurn();

    /**
     * Select one of your's workers.
     *
     * @param workersPositions the workers positions
     * @return the index of the selected worker
     */
    int selectWorker(List<Position> workersPositions);

    /**
     * Error while selecting worker.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhileSelectingWorker(String gameManagerSays);

    /**
     * Worker selected successfully.
     */
    void workerSelectedSuccessfully();

    /**
     * Chose action to perform when there are more option.
     *
     * @param possibleActions the possible actions to perform
     * @return the action choose
     */
    PossibleClientAction choseActionToPerform(List<PossibleClientAction> possibleActions);

    /**
     * Error while activating power.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhileActivatingPower(String gameManagerSays);

    /**
     * Power activated.
     *
     * @param god the god
     */
    void powerActivated(God god);

    /**
     * Move worker.
     *
     * @param nearlyPosValid the nearly pos valid to move
     * @return the position where I moved my worker
     */
    Position moveWorker(List<Position> nearlyPosValid);

    /**
     * Error while moving worker.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhileMovingWorker(String gameManagerSays);

    /**
     * Ask to the player if he wants to move again
     * true if yes
     * false if no
     *
     * @return the boolean
     */
    boolean wantMoveAgain();

    /**
     * Show server's message if I can move again.
     *
     * @param gameManagaerSays contains the message from server to show
     */
    void printCanMoveAgain(String gameManagaerSays);

    /**
     * Worker moved successfully.
     */
    void workerMovedSuccessfully();

    /**
     * End move request error.
     *
     * @param gameManagerSays contains the error from server
     */
    void endMoveRequestError(String gameManagerSays);

    /**
     * End moving phase.
     *
     * @param gameManagerSays contains the message from server to show
     */
    void endMovingPhase(String gameManagerSays);

    /**
     * Build position.
     *
     * @param possiblePosToBuild the possible pos to build
     * @return the position where I built with my selected worker
     */
    Position build(ArrayList<Position> possiblePosToBuild);

    /**
     * Ask to the player if he wants to build again
     * true if yes
     * false if no
     *
     * @return the boolean
     */
    boolean wantBuildAgain();

    /**
     * Show server's message if I can build again.
     *
     * @param gameManagerSays contains the message from server to show
     */
    void printCanBuildAgain(String gameManagerSays);

    /**
     * Error while building.
     *
     * @param gameManagerSays contains the error from server
     */
    void errorWhileBuilding(String gameManagerSays);

    /**
     * Built successfully.
     */
    void builtSuccessfully();

    /**
     * End build request error.
     *
     * @param gameManagerSays contains the error from server
     */
    void endBuildRequestError(String gameManagerSays);

    /**
     * End building phase.
     *
     * @param gameManagerSays contains the message from server to show
     */
    void endBuildingPhase(String gameManagerSays);

    /**
     * End turn.
     */
    void endTurn();

    /**
     * Someone else doing stuff.
     */
    void someoneElseDoingStuff();

    /**
     * Another player is picking up god.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerIsPickingUpGod(String turnOwner);

    /**
     * Another player is placing worker.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerIsPlacingWorker(String turnOwner);

    /**
     * Another player starting his turn.
     *
     * @param turnOwner the turn owner
     */
    void startingPlayerTurn(String turnOwner);

    /**
     * Another player is selecting worker.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerIsSelectingWorker(String turnOwner);

    /**
     * Another player is moving.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerIsMoving(String turnOwner);

    /**
     * Another player is building.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerIsBuilding(String turnOwner);

    /**
     * Another player has selected gods.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerHasSelectedGods(String turnOwner);

    /**
     * Another player has picked up god.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerHasPickedUpGod(String turnOwner);

    /**
     * Another player has placed worker.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerHasPlacedWorker(String turnOwner);

    /**
     * Another player has selected worker.
     *
     * @param serverResponse the server response
     */
    void anotherPlayerHasSelectedWorker(SelectWorkerServerResponse serverResponse);

    /**
     * Another player has moved.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerHasMoved(String turnOwner);

    /**
     * Another player has built.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerHasBuilt(String turnOwner);

    /**
     * Another player has ended his turn.
     *
     * @param turnOwner the turn owner
     */
    void anotherPlayerHasEndedHisTurn(String turnOwner);

    /**
     * You win.
     */
    void youWin();

    /**
     * If lose because I'm stuck but the game continue in 3 players's game.
     */
    void iLose();

    /**
     * Someone has lost.
     *
     * @param loser the loser
     */
    void someoneHasLost(String loser);

    /**
     * Player left the game.
     *
     * @param user the username of player who left the game
     */
    void playerLeftTheGame(String user);

    /**
     * Close client.
     */
    void closeClient();

    /**
     * You lose if someone win the game.
     *
     * @param winner the winner name
     */
    void youLose(String winner);

    /**
     * Debug.
     *
     * @param serverResponse the server response
     */
    void debug(ServerResponse serverResponse);

    /**
     * Show map.
     *
     * @param clientMap the client map to show
     */
    void showMap(GameMap clientMap);
}
