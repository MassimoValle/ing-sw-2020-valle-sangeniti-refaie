package it.polimi.ingsw.Server.Model.God;

import it.polimi.ingsw.Server.Model.God.GodsPower.Power;

import java.io.Serializable;

public class God implements Serializable {

    private String godName;
    private String godDescription;
    private Power godPower;

    //if one God is assigned to one player
    private boolean assigned;


    public God(String godName, String godDescription, Power godPower){
        this.godName = godName;
        this.godDescription = godDescription;
        this.godPower = godPower;
        this.assigned = false;

    }


    public  boolean isAssigned() {
        return assigned;
    }

    public  void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getGodName() { return godName; }
    public String getGodDescription() {return godDescription; }


    public Power getGodPower() {
        return godPower;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof God)) {
            return false;
        }

        return  this.godName.equals(((God) o).godName); /*&&
                this.godDescription.equals(((God) o).godDescription) &&
                this.godPower.equals(((God) o).godPower);*/
    }

    @Override
    public String toString() {
        String string = "";
        string = string.concat("\nName: " + this.getGodName());
        string = string.concat("\nDescription: " + this.getGodDescription());
        string = string.concat("\nPower description: " + this.getGodPower().getPowerDescription());
        return string;
    }


}
