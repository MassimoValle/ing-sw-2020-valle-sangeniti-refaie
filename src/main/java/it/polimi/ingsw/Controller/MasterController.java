package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Responses.Response;
import it.polimi.ingsw.Network.Message.Responses.WonResponse;


public class MasterController {

    private static SetUpGameManager setUpGameManager;
    private final TurnManager turnManager;
    private static ActionManager actionManager;

    private static Game gameInstance;





    public MasterController(Game gameInstance, Player activePlayer){

        MasterController.gameInstance = gameInstance;

        setUpGameManager = new SetUpGameManager(gameInstance, activePlayer);
        turnManager = new TurnManager(gameInstance.getPlayers());
        actionManager = new ActionManager(gameInstance, turnManager);

    }


    public Game getGameInstance() {
        return gameInstance;
    }


    /**
     * It dispatch the request based on game phases
     *
     * @param request the request sent by the client
     */
    public static void dispatcher(Request request){

        switch (request.getMessageDispatcher()) {
            case SETUP_GAME -> setUpGameManager.handleMessage(request);
            case TURN -> actionManager.handleRequest(request);
        }

    }





    // FIXME: da decidere se tenere qui

    /**
     * Build negative response.
     *
     * @param gameManagerSays the message from the Game Manager
     * @return the response
     */
    public static Response buildNegativeResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        Response res = new Response(player.getPlayerName(), responseContent, MessageStatus.ERROR, gameManagerSays);
        gameInstance.putInChanges(player, res);

        return res;

    }

    /**
     * Build Positive response.
     *
     * @param gameManagerSays the message from the Game Manager
     * @return the response
     */
    public static Response buildPositiveResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        Response res = new Response(player.getPlayerName(), responseContent, MessageStatus.OK, gameManagerSays);
        gameInstance.putInChanges(player, res);

        return res;
    }

    //Fa la stessa identica cosa del buildPositiveResponse solo che invia una WonResponse
    public static Response buildWonResponse(Player player, String gameManagerSays) {

        Response res = new WonResponse(player.getPlayerName(), gameManagerSays);
        gameInstance.putInChanges(player, res);

        //da notificare a tutti gli altri giocatori che un player ha vinto --> fine partita

        return res;
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
