package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

import java.io.Serializable;

public class ApolloPower extends Power implements Serializable {

    public ApolloPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public boolean[] move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        //Controllo se attiva il potere di Apollo
        if (squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(activeWorker.getColor()) ) {

            Worker worker = squareWhereToMove.getWorkerOnSquare();
            //Prima devo liberare lo squareWhereToMove altrimenti la riga 26 non verrà eseguita
            squareWhereToMove.freeSquare();
            Position pos = activeWorker.getWorkerPosition();

            //Sposto il worker del player che ha apollo
            super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            //Sposto il worker che si deve mettere al posto di apollo
            super.move(worker, pos, squareWhereToMove, squareWhereTheWorkerIs);

            return actionDone();

        }

        //Faccio la classica moveAction
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

}

