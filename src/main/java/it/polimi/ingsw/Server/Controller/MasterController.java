package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Network.Message.ClientRequests.Request;


public class MasterController {

    private SetUpGameManager setUpGameManager;
    private TurnManager turnManager;
    private ActionManager actionManager;

    private final Game gameInstance;





    public MasterController(Game gameInstance){

        this.gameInstance = gameInstance;

    }

    public void init(Player activePlayer) {

        this.setUpGameManager = new SetUpGameManager(gameInstance, activePlayer);

        this.turnManager = new TurnManager(gameInstance.getPlayers());

        this.actionManager = new ActionManager(gameInstance, turnManager);

    }

    // getter
    public Game getGameInstance() {
        return gameInstance;
    }



    // inizio e fine
    public void startFirstRound() {
        actionManager.startNextRound(true);
    }

    public void gameOver() {

        Server.cleanLobby();
        Server.refresh();

    }




    /**
     * It dispatch the request based on game phases
     *
     * @param request the request sent by the client
     */
    public void dispatcher(Request request){

        switch (request.getMessageDispatcher()) {
            case SETUP_GAME -> {
                if(setUpGameManager.handleMessage(request)) startFirstRound();
            }
            case TURN -> actionManager.handleRequest(request);
        }

    }




    //      ####    TESTING-ONLY    ####
    public SetUpGameManager _getSetUpGameController() {
        return setUpGameManager;
    }
    public ActionManager _getActionManager() {
        return actionManager;
    }
    public TurnManager _getTurnManager() {
        return turnManager;
    }
}
