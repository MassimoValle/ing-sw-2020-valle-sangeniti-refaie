package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Response;


public class SuperMegaController {

    private final SetUpGameController setUpGameController;
    private final TurnManager turnManager;
    private final ActionManager actionManager;

    private static Game game;

    public static PossibleGameState gameState;





    public SuperMegaController(Game game, Player activePlayer){

        SuperMegaController.game = game;

        setUpGameController = new SetUpGameController(game, activePlayer);
        turnManager = new TurnManager(game.getPlayers());
        actionManager = new ActionManager(game, turnManager);

    }





    public void dispatcher(Request request){

        switch (request.getMessageDispatcher()) {
            case SETUP_GAME -> setUpGameController.handleMessage(request);
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
    public static Response buildNegativeResponse(Player player, MessageContent messageContent, String gameManagerSays) {

        Response res = new Response(player.getPlayerName(), messageContent, MessageStatus.ERROR, gameManagerSays);
        game.putInChanges(player, res);

        return res;

    }

    /**
     * Build Positive response.
     *
     * @param gameManagerSays the message from the Game Manager
     * @return the response
     */
    public static Response buildPositiveResponse(Player player, MessageContent messageContent, String gameManagerSays) {

        Response res = new Response(player.getPlayerName(), messageContent, MessageStatus.OK, gameManagerSays);
        game.putInChanges(player, res);

        return res;
    }

}
