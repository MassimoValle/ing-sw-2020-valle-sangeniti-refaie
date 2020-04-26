package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.UserPlayerState;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.Requests.BuildRequest;
import it.polimi.ingsw.Network.Message.Requests.EndRequest;
import it.polimi.ingsw.Network.Message.Requests.MoveRequest;
import it.polimi.ingsw.Network.Message.Requests.SelectWorkerRequest;


public class TurnManager {


    private static Game game;

    private Player player;
    private Worker selectedWorker;

    private static int currentPlayer = 0;

    private UserPlayerState userPlayerState;


    public TurnManager(Game game) {

        userPlayerState = UserPlayerState.STARTING_TURN;

        this.game = game;
        this.player = GameManager.activePlayer;

    }



    // setter
    public void setSelectedWorker(Worker Worker) {
        this.selectedWorker = Worker;
    }



    // function
    public static Player nextPlayer() {
        return game.getPlayers().get((currentPlayer % game.getPlayers().size()) +1);
    }




    // handler
    public void handleMessage(Message message) {

        if(!GameManager.checkTurnOwnership(message.getMessageSender()))
            return;

        switch (message.getMessageContent()){

            case SELECT_WORKER -> handleSelectWorkerAction((SelectWorkerRequest) message);

            case MOVE -> handleMoveAction((MoveRequest) message);

            case BUILD -> handleBuildAction((BuildRequest) message);

            case END_OF_TURN -> handleEndAction((EndRequest) message);
        }
    }

    private void handleSelectWorkerAction(SelectWorkerRequest request) {

        if(userPlayerState != UserPlayerState.STARTING_TURN) return;

        Worker workerFromRequest = request.getWorkerToSelect();

        Action selectWorkerAction = new SelectWorkerAction(player, workerFromRequest);


        if (workerFromRequest.isPlaced()) {

            if (!game.getGameMap().isWorkerStuck(workerFromRequest)) {
                selectWorkerAction.doAction();
                this.setSelectedWorker(workerFromRequest);

                userPlayerState = UserPlayerState.WORKER_SELECTED;
            }

            // else ritorna errore

        }
    }

    private void handleMoveAction(MoveRequest request) {

        if(userPlayerState != UserPlayerState.WORKER_SELECTED) return;

        Position positionWhereToMove = request.getSenderMovePosition();


        Square squareWhereTheWorkerIs = game.getGameMap().getSquare(selectedWorker.getWorkerPosition());
        Square squareWhereToMove = game.getGameMap().getSquare(positionWhereToMove);

        Action moveAction = new MoveAction(player, selectedWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);


        if (moveAction.isValid()) {
            moveAction.doAction();

            userPlayerState = UserPlayerState.WORKER_MOVED;
        }

        // else ritorna errore


        //setGameState(PossibleGameState.ACTION_DONE);
    }

    private void handleBuildAction(BuildRequest request) {

        if(userPlayerState != UserPlayerState.WORKER_MOVED) return;

        Position positionWhereToBuild = request.getPositionWhereToBuild();


        Square squareWhereToBuild = game.getGameMap().getSquare(positionWhereToBuild);

        Action buildAction = new BuildAction(squareWhereToBuild);


        if (buildAction.isValid()) {
            buildAction.doAction();

            userPlayerState = UserPlayerState.WORKER_BUILT;
        }

        // else ritorna errore

        //setGameState(PossibleGameState.ACTION_DONE);
    }

    private void handleEndAction(EndRequest request) {

        userPlayerState = UserPlayerState.ENDING_TURN;

        // set GameManager attributes
        GameManager.lastActivePlayer = player;
        GameManager.lastActiveWorker = selectedWorker;

        // set local attributes
        player = nextPlayer();
        selectedWorker = null;
        currentPlayer++;

        userPlayerState = UserPlayerState.STARTING_TURN;

    }

}