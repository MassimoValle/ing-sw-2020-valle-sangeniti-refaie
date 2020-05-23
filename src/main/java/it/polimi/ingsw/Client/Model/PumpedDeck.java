package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.GodsPower.*;
import javafx.scene.image.Image;


public class PumpedDeck extends Deck {

    private static PumpedDeck instance=null;
    private String path = "/imgs/godCards/";

    public PumpedDeck() {
        loadXML();
    }

    public static PumpedDeck getInstance() {
        if(instance==null)
            instance = new PumpedDeck();
        return instance;
    }

    protected PumpedGod createGod(String godName, String godDescription, String godPowerType, String godPowerDescription) {

        return switch (godName) {
            case "Apollo" -> new PumpedGod(godName, godDescription, new ApolloPower(godPowerType, godPowerDescription), new Image(path + "01.png"));
            case "Artemis" -> new PumpedGod(godName, godDescription, new ArtemisPower(godPowerType, godPowerDescription), new Image(path + "02.png"));
            case "Athena" -> new PumpedGod(godName, godDescription, new AthenaPower(godPowerType, godPowerDescription), new Image(path + "03.png"));
            case "Atlas" -> new PumpedGod(godName, godDescription, new AtlasPower(godPowerType, godPowerDescription), new Image(path + "04.png"));
            case "Demeter" -> new PumpedGod(godName, godDescription, new DemeterPower(godPowerType, godPowerDescription), new Image(path + "05.png"));
            case "Hephaestus" -> new PumpedGod(godName, godDescription, new HephaestusPower(godPowerType, godPowerDescription), new Image(path + "06.png"));
            case "Minotaur" -> new PumpedGod(godName, godDescription, new MinotaurPower(godPowerType, godPowerDescription), new Image(path + "08.png"));
            case "Pan" -> new PumpedGod(godName, godDescription, new PanPower(godPowerType, godPowerDescription), new Image(path + "09.png"));
            case "Prometheus" -> new PumpedGod(godName, godDescription, new PrometheusPower(godPowerType, godPowerDescription), new Image(path + "10.png"));
            case "Chronus" -> new PumpedGod(godName, godDescription, new ChronusPower(godPowerType, godPowerDescription), new Image(path + "16.png"));
            case "Hera" -> new PumpedGod(godName, godDescription, new HeraPower(godPowerType, godPowerDescription), new Image(path + "20.png"));
            case "Hestia" -> new PumpedGod(godName, godDescription, new HestiaPower(godPowerType, godPowerDescription), new Image(path + "21.png"));
            case "Zeus" -> new PumpedGod(godName, godDescription, new ZeusPower(godPowerType, godPowerDescription), new Image(path + "30.png"));
            case "Triton" -> new PumpedGod(godName, godDescription, new TritonPower(godPowerType, godPowerDescription), new Image(path + "29.png"));
            default -> null;
        };
    }

}
