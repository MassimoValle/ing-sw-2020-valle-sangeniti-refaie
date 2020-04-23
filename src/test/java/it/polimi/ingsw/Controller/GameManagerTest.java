package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.MoveRequest;
import it.polimi.ingsw.Network.Message.PlaceWorkerRequest;
import it.polimi.ingsw.Network.Message.SelectWorkerRequest;
import it.polimi.ingsw.Network.Server;
import org.junit.Test;

import static org.junit.Assert.*;
import java.io.IOException;

public class GameManagerTest {

    GameManager gameManager;



    @Test
    public void gameFlowTest() throws IOException, PlayerNotFoundException {
        try {
            gameManager = new GameManager(new Server());

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(PossibleGameState.GAME_INIT, gameManager.getGameState());

        gameManager.addPlayerToCurrentGame("Simone");
        gameManager.addPlayerToCurrentGame("Massimo");
        gameManager.addPlayerToCurrentGame("Magdy");

        assertEquals(3, gameManager.getGameInstance().getNumberOfPlayers());

        Player simone = gameManager.getGameInstance().searchPlayerByName("Simone");
        Player massimo = gameManager.getGameInstance().searchPlayerByName("Massimo");
        Player magdy = gameManager.getGameInstance().searchPlayerByName("Magdy");



        gameManager.initTurnManager(gameManager.getGameInstance());
        gameManager.setNewActivePlayer(simone);



        assertEquals(gameManager.getTurnManager().getActivePlayer(), simone);

        String username = simone.getPlayerName();
        Worker worker0 = simone.getPlayerWorkers().get(0);
        Worker worker1 = simone.getPlayerWorkers().get(1);
        Position pos00 = new Position(0,0);
        Position pos01 = new Position(0,1);

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(username, worker0)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(username, worker0, pos00)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(username, worker1)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(username, worker1, pos01)
        );

        gameManager.getGameInstance().getGameMap().printBoard();

    }
}