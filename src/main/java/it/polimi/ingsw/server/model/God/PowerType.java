package it.polimi.ingsw.server.model.God;

public enum PowerType {
    YOUR_TURN ,YOUR_MOVE, YOUR_BUILD, OPPONENTS_TURN, WIN_CONDITION, END_OF_YOUR_TURN;

    public static PowerType matchFromXml(String powerType) {
        return switch (powerType) {
            case "Your Move" -> YOUR_MOVE;
            case "Opponent’s Turn" -> OPPONENTS_TURN;
            case "Your Build" -> YOUR_BUILD;
            case "Win Condition" -> WIN_CONDITION;
            case "End of Your Turn" -> END_OF_YOUR_TURN;
//Prometheus's powertype
            default -> YOUR_TURN;
        };
    }

}
