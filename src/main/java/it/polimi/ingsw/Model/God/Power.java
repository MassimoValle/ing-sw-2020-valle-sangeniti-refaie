package it.polimi.ingsw.Model.God;

public class Power {

    private String powerDescription;
    private PowerType powerType;


    public Power(String powerType, String powerDescription) {
        this.powerType = PowerType.matchFromXml(powerType);
        this.powerDescription = powerDescription;
    }

    public PowerType getPowerType() {
        return powerType;
    }

    public String getPowerDescription() {
        return powerDescription;
    }
}