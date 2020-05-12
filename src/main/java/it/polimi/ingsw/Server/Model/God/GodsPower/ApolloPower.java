package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;

public class ApolloPower extends Power implements Serializable {

    public ApolloPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

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

            return ActionOutcome.DONE;

        }

        //Faccio la classica moveAction
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

}

