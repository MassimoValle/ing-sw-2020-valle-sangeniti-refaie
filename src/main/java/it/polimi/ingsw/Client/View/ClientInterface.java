package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Network.Message.Server.Responses.Response;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public interface ClientInterface {


    void start();

    String askIpAddress();

    String askUserName();

    int askNumbOfPlayer();

    void showDeck();

    ArrayList<God> selectGods(int howMany);

    God pickFromChosenGods(ArrayList<God> hand);

    Position placeWorker(String worker);

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

    void win(boolean winner);

    void debug(Response response);


}
