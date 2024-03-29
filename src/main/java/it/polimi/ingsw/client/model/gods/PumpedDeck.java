package it.polimi.ingsw.client.model.gods;

import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.god.godspower.*;
import javafx.scene.image.Image;


/**
 * The type Pumped deck contains gods with the ImageView set.
 */
public class PumpedDeck extends Deck {

    /**
     * Instantiates a new Pumped deck initializing the graphics resources.
     *
     */
    public PumpedDeck() { }

    @Override
    protected God createGod(String godName, String godDescription, String godPowerType, String godPowerDescription) {

        String path = "/imgs/godCards/";

        return switch (godName) {
            case "Apollo" -> new God(godName, godDescription, new ApolloPower(godPowerType, godPowerDescription), new Image(path + "01.png"));
            case "Artemis" -> new God(godName, godDescription, new ArtemisPower(godPowerType, godPowerDescription), new Image(path + "02.png"));
            case "Athena" -> new God(godName, godDescription, new AthenaPower(godPowerType, godPowerDescription), new Image(path + "03.png"));
            case "Atlas" -> new God(godName, godDescription, new AtlasPower(godPowerType, godPowerDescription), new Image(path + "04.png"));
            case "Demeter" -> new God(godName, godDescription, new DemeterPower(godPowerType, godPowerDescription), new Image(path + "05.png"));
            case "Hephaestus" -> new God(godName, godDescription, new HephaestusPower(godPowerType, godPowerDescription), new Image(path + "06.png"));
            case "Minotaur" -> new God(godName, godDescription, new MinotaurPower(godPowerType, godPowerDescription), new Image(path + "08.png"));
            case "Pan" -> new God(godName, godDescription, new PanPower(godPowerType, godPowerDescription), new Image(path + "09.png"));
            case "Prometheus" -> new God(godName, godDescription, new PrometheusPower(godPowerType, godPowerDescription), new Image(path + "10.png"));
            case "Chronus" -> new God(godName, godDescription, new ChronusPower(godPowerType, godPowerDescription), new Image(path + "16.png"));
            case "Hera" -> new God(godName, godDescription, new HeraPower(godPowerType, godPowerDescription), new Image(path + "20.png"));
            case "Hestia" -> new God(godName, godDescription, new HestiaPower(godPowerType, godPowerDescription), new Image(path + "21.png"));
            case "Zeus" -> new God(godName, godDescription, new ZeusPower(godPowerType, godPowerDescription), new Image(path + "30.png"));
            case "Triton" -> new God(godName, godDescription, new TritonPower(godPowerType, godPowerDescription), new Image(path + "29.png"));
            default -> null;
        };
    }

}
