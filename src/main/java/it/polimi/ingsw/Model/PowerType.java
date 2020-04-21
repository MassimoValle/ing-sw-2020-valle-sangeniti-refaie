package it.polimi.ingsw.Model;

public enum PowerType {
    YOUR_TURN ,YOUR_MOVE, YOUR_BUILD, OPPONENTS_TURN, WIN_CONDITION, END_OF_YOUR_TURN;

    public static PowerType matchFromXml(String powerType) {
        switch (powerType) {
            case "Your Move":
                return YOUR_MOVE;
            case "Opponent’s Turn":
                return OPPONENTS_TURN;
            case "Your Build":
                return YOUR_BUILD;
            case "Win Condition":
                return WIN_CONDITION;
            case "End of Your Turn":
                return END_OF_YOUR_TURN;

            default:
                return YOUR_TURN;    //Prometheus's powertype
        }
    }

}
