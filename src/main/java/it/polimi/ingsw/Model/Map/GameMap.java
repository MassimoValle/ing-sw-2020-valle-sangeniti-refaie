package it.polimi.ingsw.Model.Map;


import it.polimi.ingsw.Model.Player.Position;

public class GameMap {

    public final int COLUMNS = 5;
    public final int RAWS = 5;

    private Square[][] board;

   public GameMap() {
       this.board = buildBoard();
       for (int i=0; i<COLUMNS; i++) {
           for ( int j=0; j<RAWS; j++) {
               this.board[i][j] = new Square();
           }
       }
   }

    public Square[][] getBoard() {
        return this.board;
    }

    private Square[][] buildBoard() {
       return new Square[COLUMNS][RAWS];
    }

    public int getSquareHeight(Position position) {
        return board[position.getColumn()][position.getRaw()].getHeight();
    }

    public void setSquareHeight(Position position) {
        board[position.getColumn()][position.getRaw()].heightPlusOne();
    }


    public void addBlock(Position position) {
        if (getSquareHeight(position) < 4 ) {
            setSquareHeight(position);
        } else {
            System.out.println("There is already a Dome!");
        }
    }




    @Override
    public String toString() {
       for (int i=0; i<RAWS; i++) {
           for ( int j=0; j<COLUMNS; j++) {
               System.out.println("Riga:" + i + "Colonna:" + j);
           }
       }
        return null;
    }

}
