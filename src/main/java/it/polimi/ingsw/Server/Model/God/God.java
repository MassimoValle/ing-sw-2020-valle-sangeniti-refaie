package it.polimi.ingsw.Server.Model.God;

import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class God implements Serializable {

    private final String godName;
    private final String godDescription;
    private final Power godPower;

    //if one God is assigned to one player
    private boolean assigned;

    //GUI
    transient private final ImageView imgView;


    public God(String godName, String godDescription, Power godPower, Image img){
        this.godName = godName;
        this.godDescription = godDescription;
        this.godPower = godPower;
        this.assigned = false;

        this.imgView = new ImageView(img);

    }

    public ImageView getImgView() {
        return imgView;
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


    public boolean is(String name) {
        String godName = this.getGodName();
        return godName.equals(name);
    }
}
