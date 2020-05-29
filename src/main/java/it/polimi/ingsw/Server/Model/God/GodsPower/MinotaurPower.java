package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;


public class MinotaurPower extends Power {

    private  Position backWardPosition;

    public MinotaurPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker minotaurWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        Position actualPosition = squareWhereTheWorkerIs.getPosition();


        if(squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(minotaurWorker.getColor()) ){

            //salvo il worker avversario
            Worker opponentWorker = squareWhereToMove.getWorkerOnSquare();
            Position opponentWorkerPosition = opponentWorker.getWorkerPosition();
            Square opponentWorkerStartingSquare = squareWhereToMove;



            findBackWardSPosition(actualPosition, positionWhereToMove);
            Square backWardSquare = Game.getInstance().getGameMap().getSquare(backWardPosition);
            //sposto worker avversario
            super.move(opponentWorker, backWardPosition, opponentWorkerStartingSquare, backWardSquare);

            //sposto worker che ha minotauro
            super.move(minotaurWorker, opponentWorkerPosition, squareWhereTheWorkerIs, opponentWorkerStartingSquare);


            return ActionOutcome.DONE;

        }return super.move(minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);




    }



    private void findBackWardSPosition(Position pos1, Position pos2){

        int x1 = pos1.getRow();
        int x2 = pos2.getRow();
        int y1 = pos1.getColumn();
        int y2 = pos2.getColumn();
        int x3,y3;



        if (x1 == x2){
            if (y1 < y2){
                x3 = x2;
                y3 = y2 + 1;
                backWardPosition = new Position(x3,y3);
            }else {         //y1 > y2 non ha senso vedere quando sono uguali.
                x3 = x2;
                y3 = y2 - 1;
            }       backWardPosition = new Position(x3,y3);
        }else if (x1 < x2) {
            if (y1 < y2) {
                x3 = x2 + 1;
                y3 = y2 + 1;
                backWardPosition = new Position(x3,y3);
            }else if (y1 == y2){
                x3 = x2 + 1;
                y3 = y2;
                backWardPosition = new Position(x3,y3);
            }else {
                x3 = x2 + 1;
                y3 = y2 - 1;
                backWardPosition = new Position(x3,y3);
            }
        }else {
            if(y1 < y2){
                x3 = x2 - 1;
                y3 = y2 + 1;
                backWardPosition = new Position(x3,y3);
            }else if (y1 == y2){
                x3 = x2 -1;
                y3 = y2;
                backWardPosition = new Position(x3,y3);
            }else {
                x3 = x2 - 1;
                y3 = y2 - 1;
                backWardPosition = new Position(x3,y3);
            }
        }
    }
}

