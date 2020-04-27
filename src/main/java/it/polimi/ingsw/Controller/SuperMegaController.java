package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Requests.Request;

public class SuperMegaController {

    private final SetUpGameController setUpGameController;
    private final TurnManager turnManager;

    public SuperMegaController(Game game, Player activePlayer){

        setUpGameController = new SetUpGameController(game, activePlayer);
        turnManager = new TurnManager(game);

    }

    public void dispatcher(Request message){

        switch (message.getMessageDispatcher()) {
            case SETUP_GAME -> setUpGameController.handleMessage(message);
            case TURN -> turnManager.handleMessage(message);
        }

    }
}
