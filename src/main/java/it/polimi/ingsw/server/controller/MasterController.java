package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.network.message.clientrequests.Request;


/**
 * The master controller which allow every communication between every different controllers in the mvC pattern
 */
public class MasterController {

    private SetUpGameManager setUpGameManager;
    private TurnManager turnManager;
    private ActionManager actionManager;
    private OutgoingMessageManager messageManager;


    private final Game gameInstance;
    private final Server server;





    public MasterController(Game gameInstance, Server server){

        this.gameInstance = gameInstance;
        this.server = server;

    }

    /**
     * It is called whenever a new {@link Game} is about to start
     *
     * @param godLikePlayer the godlike player
     */
    public void init(Player godLikePlayer) {

        this.turnManager = new TurnManager(gameInstance.getPlayers());

        this.messageManager = new OutgoingMessageManager(gameInstance, turnManager);

        this.setUpGameManager = new SetUpGameManager(gameInstance, godLikePlayer, messageManager);


        this.actionManager = new ActionManager(gameInstance, turnManager, messageManager);

    }

    // getter
    public Game getGameInstance() {
        return gameInstance;
    }


    /**
     * This method is called when the setup phase is done
     */
    public void startFirstRound() {
        actionManager.startNextRound(true);
    }

    /**
     * Client connection exception handles the disconnection of any player
     *
     * @param userDisconnected the user disconnected
     */
    public void clientConnectionException(String userDisconnected) {

        Player playerDisconnected = gameInstance.searchPlayerByName(userDisconnected);

        messageManager.buildNegativeResponse(playerDisconnected, ResponseContent.DISCONNECT, "");

       if (gameInstance.getPlayers().size() <=2) {
           gameOver();
       }
    }

    /**
     * The game is over
     */
    public void gameOver() {

        server.cleanLobby(this);

    }




    /**
     * It dispatch the request based on game phases
     *
     * @param request the request sent by the client
     */
    public void dispatcher(Request request){

        if (request.getMessageDispatcher() == Dispatcher.SETUP_GAME) {

            if(setUpGameManager.handleMessage(request))
                startFirstRound();

        } else if (request.getMessageDispatcher() == Dispatcher.TURN) {

            actionManager.handleRequest(request);
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
