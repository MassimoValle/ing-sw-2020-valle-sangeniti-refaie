package it.polimi.ingsw.Model;

public class Power {

    private String powerDescription;
    private PowerType powerType;


    public Power(String powerType, String powerDescription) {
        this.powerType = matchFromXml(powerType);
        this.powerDescription = powerDescription;
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    public PowerType getPowerType() {
        return powerType;
    }

    public PowerType matchFromXml(String powerType) {
        switch (powerType) {
            case "Your Move":
                return PowerType.YOUR_MOVE;
            case "Opponent’s Turn":
                return PowerType.OPPONENTS_TURN;
            case "Your Build":
                return PowerType.YOUR_BUILD;
            case "Win Condition":
                return PowerType.WIN_CONDITION;

            default:
                return PowerType.YOUR_TURN;    //Prometheus's powertype
        }
    }
}