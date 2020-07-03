package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.god.godspower.Power;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Objects;

/**
 * Thi class represent the God card containing ALL the information about every God
 */
public class God implements Serializable {

    private final String godName;
    private final String godDescription;
    private final Power godPower;

    //if one God is assigned to one player
    private boolean assigned;

    //GUI
    transient private ImageView imgView;


    public God(String godName, String godDescription, Power godPower, Image img){
        this.godName = godName;
        this.godDescription = godDescription;
        this.godPower = godPower;
        this.assigned = false;

        if(img != null)
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

    /*
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
                this.godPower.equals(((God) o).godPower);
    }

     */


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof God)) return false;
        God god = (God) o;
        return getGodName().equals(god.getGodName()) &&
                getGodDescription().equals(god.getGodDescription()) &&
                getGodPower().equals(god.getGodPower());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGodName(), getGodDescription(), getGodPower());
    }
}
