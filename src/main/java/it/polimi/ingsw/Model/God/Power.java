package it.polimi.ingsw.Model.God;

public class Power {

    private String powerDescription;
    private PowerType powerType;

    private boolean canTakeOpponentsPlace;


    public Power(String powerType, String powerDescription) {
        this.powerType = PowerType.matchFromXml(powerType);
        this.powerDescription = powerDescription;
    }

    public Power(boolean canTakeOpponentsPlace) {
        this.canTakeOpponentsPlace = canTakeOpponentsPlace;
    }

    public PowerType getPowerType() {
        return powerType;
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    public static class ApolloPower extends Power {

        public ApolloPower() {
            super(true);
        }
    }

}