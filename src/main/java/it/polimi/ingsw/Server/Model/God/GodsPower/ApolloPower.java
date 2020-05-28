package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;

public class ApolloPower extends Power {

    private Square apolloStartingSquare;
    private Square opponentWorkerStartingSquare;
    private Position apolloStartingPosition;
    private Position opponentWorkerPosition;

    public ApolloPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker apolloWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        //Controllo se attiva il potere di Apollo
        if (squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(apolloWorker.getColor()) ) {

            apolloStartingSquare = squareWhereTheWorkerIs;
            opponentWorkerStartingSquare = squareWhereToMove;

            Worker opponentWorker = opponentWorkerStartingSquare.getWorkerOnSquare();
            opponentWorkerPosition = opponentWorker.getWorkerPosition();
            apolloStartingPosition = apolloWorker.getWorkerPosition();

            opponentWorker.setPosition(null);
            opponentWorkerStartingSquare.freeSquare();

            ActionOutcome outcome = super.move(apolloWorker, opponentWorkerPosition, apolloStartingSquare, opponentWorkerStartingSquare);

            if (outcome == ActionOutcome.NOT_DONE) {
                return ActionOutcome.NOT_DONE;
            }

            super.move(opponentWorker, apolloStartingPosition, opponentWorkerStartingSquare, apolloStartingSquare);


            opponentWorkerStartingSquare.setWorkerOn(apolloWorker);
            apolloStartingSquare.setWorkerOn(opponentWorker);
            opponentWorker.setPosition(apolloStartingPosition);

            return ActionOutcome.DONE;

        }

        //Faccio la classica moveAction
        return super.move(apolloWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public boolean powerMustBeReset() {
        return true;
    }

    @Override
    public void resetPower() {
        apolloStartingSquare = null;
        opponentWorkerStartingSquare = null;
        apolloStartingPosition = null;
        opponentWorkerPosition = null;
    }
}

