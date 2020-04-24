package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Server;
import org.junit.Test;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;

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



        String simo = simone.getPlayerName();
        Worker worker0 = simone.getPlayerWorkers().get(0);
        Worker worker1 = simone.getPlayerWorkers().get(1);

        String max = massimo.getPlayerName();
        Worker worker0max = massimo.getPlayerWorkers().get(0);
        Worker worker1max = massimo.getPlayerWorkers().get(1);

        String mag = magdy.getPlayerName();
        Worker worker0mag = magdy.getPlayerWorkers().get(0);
        Worker worker1mag = magdy.getPlayerWorkers().get(1);


        Position pos00 = new Position(0,0);
        Position pos01 = new Position(0,1);
        Position pos02 = new Position(0,2);
        Position pos03 = new Position(0,3);
        Position pos10 = new Position(1,0);
        Position pos11 = new Position(1,1);

        //HO SCELTO I GOD CHE FARANNO PARTE DELLA PARTITA
        gameManager.setGameState(PossibleGameState.READY_TO_PLAY);

        ArrayList<God> chosenGod = new ArrayList<>();

        chosenGod.add(gameManager.getGameInstance().getDeck().getGod(0));
        chosenGod.add(gameManager.getGameInstance().getDeck().getGod(1));
        chosenGod.add(gameManager.getGameInstance().getDeck().getGod(2));


        gameManager.notifyTheGodLikePlayer();
        gameManager.setNewActivePlayer(simone);
        gameManager.setGameState(PossibleGameState.GODLIKE_PLAYER_MOMENT);

        gameManager.handleMessage(
                new ChoseGodsRequest(simo, chosenGod )
        );


        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(simo, worker0)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(simo, worker0, pos00)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(simo, worker1)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(simo, worker1, pos01)
        );

        gameManager.getGameInstance().getGameMap().printBoard();

        //Simone ha finito di piazzare i propri worker tocca a Max

        gameManager.getTurnManager().nextTurn(massimo);
        assertEquals(gameManager.getTurnManager().getActivePlayer(), massimo);

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(max, worker0max)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(max, worker0max, pos10)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(max, worker1max)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(max, worker1max, pos11)
        );

        gameManager.getGameInstance().getGameMap().printBoard();


        //Max ha piazzato i suoi worker tocca a Magdy

        gameManager.getTurnManager().nextTurn(magdy);
        assertEquals(gameManager.getTurnManager().getActivePlayer(), magdy);

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(mag, worker0mag)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(mag, worker0mag, new Position(4,4))
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new SelectWorkerRequest(mag, worker1mag)
        );

        gameManager.setGameState(PossibleGameState.PLACING_WORKERS);

        gameManager.handleMessage(
                new PlaceWorkerRequest(mag, worker1mag, new Position(4,3))
        );

        gameManager.getGameInstance().getGameMap().printBoard();

        //Magdy ha piazzato i suoi worker, può iniziare  la partita vera e propria

        gameManager.getTurnManager().nextTurn(simone);
        assertEquals(gameManager.getTurnManager().getActivePlayer(), simone);

        gameManager.setGameState(PossibleGameState.SELECTING_WORKER);

        assertTrue(gameManager.getGameInstance().getGameMap().isWorkerStuck(worker0));
        gameManager.handleMessage(
                new SelectWorkerRequest(simo, worker0)
        );
    }
}