package it.polimi.ingsw.Model;

public class Power {

    private String powerDescription;
    private PowerType powerType;


    public Power(String powerType, String powerDescription) {
        this.powerType  = Enum.valueOf(PowerType.class, powerType);
        this.powerDescription = powerDescription;
    }
}
