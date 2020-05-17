package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;


public class MinotaurPower extends Power {

    private static Position actualPosition;
    private static Position backWardPosition = null;

    public MinotaurPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        actualPosition = squareWhereTheWorkerIs.getPosition();


        if(squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(activeWorker.getColor()) ){

            //salvo il worker avversario
            Worker oppWorker = squareWhereToMove.getWorkerOnSquare();
            squareWhereToMove.freeSquare();
            //sposto worker che ha minotauro
            super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            //sposto worker avversario
            Square backWardSquare = Game.getInstance().getGameMap().getSquare(backWardPosition);
            super.move(oppWorker, backWardPosition, squareWhereToMove, backWardSquare);

            return ActionOutcome.DONE;

        }return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);




    }

    /**
     *
     * @param actualPosition
     * @param positionWhereToMove
     */

    private void findBackWardSPosition(Position actualPosition, Position positionWhereToMove){

        int x1 = actualPosition.getRow();
        int x2 = positionWhereToMove.getRow();
        int y1 = actualPosition.getColumn();
        int y2 = positionWhereToMove.getColumn();
        int x3 = backWardPosition.getRow();
        int y3 = backWardPosition.getColumn();



        if (x1 == x2){
            if (y1 < y2){
                x3 = x2;
                y3 = y2 + 1;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }else {         //y1 > y2 non ha senso vedere quando sono uguali.
                x3 = x2;
                y3 = y2 - 1;
            }       backWardPosition.setRow(x3);
            backWardPosition.setColumn(y3);
        }else if (x1 < x2) {
            if (y1 < y2) {
                x3 = x2 + 1;
                y3 = y2 + 1;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }else if (y1 == y2){
                x3 = x2 + 1;
                y3 = y2;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }else {
                x3 = x2 + 1;
                y3 = y2 - 1;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }
        }else {
            if(y1 < y2){
                x3 = x2 - 1;
                y3 = y2 + 1;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }else if (y1 == y2){
                x3 = x2 -1;
                y3 = y2;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }else {
                x3 = x2 - 1;
                y3 = y2 - 1;
                backWardPosition.setRow(x3);
                backWardPosition.setColumn(y3);
            }
        }
    }
}

