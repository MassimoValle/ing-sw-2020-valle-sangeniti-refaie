package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Connection;
import it.polimi.ingsw.Network.Message.Requests.Request;

import java.util.Map;

public class SuperMegaController {

    private final SetUpGameController setUpGameController;
    private final TurnManager turnManager;
    private final ActionManager actionManager;

    private Game game;

    public SuperMegaController(Game game, Player activePlayer){

        this.game = game;

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
}
