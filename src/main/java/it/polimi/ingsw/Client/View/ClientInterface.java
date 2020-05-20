package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;
import java.util.Set;

public interface ClientInterface {


    void start();

    String askIpAddress();

    String askUserName();

    int askNumbOfPlayer();

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

    int selectWorker();

    void errorWhileSelectingWorker(String gameManagerSays);

    void workerSelectedSuccessfully();

    Position moveWorker(ArrayList<Position> nearlyPosValid);

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

    void win(boolean winner);

    void debug(ServerResponse serverResponse);

    void showMap(GameMap clientMap);
}
