package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.server.model.god.godspower.*;
import it.polimi.ingsw.server.model.map.GameMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Deck extends ArrayList<God> implements Serializable {

    protected transient GameMap map;

    public Deck(GameMap map) {
        this.map = map;
        loadXML();
    }

    protected boolean loadXML(){

        InputStream in = getClass().getResourceAsStream("/XMLs/gods.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            String feature = "http://apache.org/xml/features/disallow-doctype-decl";
            dbFactory.setFeature(feature, true);
        } catch (ParserConfigurationException e) {
            Server.LOGGER.severe(e.getMessage());
        }

        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("god");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element eElement = (Element) nodeList.item(i);

                String godName = eElement.getElementsByTagName("name").item(0).getTextContent();
                String godDescription = eElement.getElementsByTagName("description").item(0).getTextContent();
                String godPowerType = eElement.getElementsByTagName("powerType").item(0).getTextContent();
                String godPowerDescription = eElement.getElementsByTagName("powerDescription").item(0).getTextContent();

                God tmp = createGod(godName, godDescription, godPowerType, godPowerDescription);

                this.add(tmp);
            }
            return true;
        } catch (Exception e) {
            Server.LOGGER.severe(e.getMessage());
            return false;
        }
    }

    protected God createGod(String godName, String godDescription, String godPowerType, String godPowerDescription) {

        return switch (godName) {
            case "Apollo" -> new God(godName, godDescription, new ApolloPower(godPowerType, godPowerDescription), null);
            case "Artemis" -> new God(godName, godDescription, new ArtemisPower(godPowerType, godPowerDescription), null);
            case "Athena" -> new God(godName, godDescription, new AthenaPower(godPowerType, godPowerDescription), null);
            case "Atlas" -> new God(godName, godDescription, new AtlasPower(godPowerType, godPowerDescription), null);
            case "Demeter" -> new God(godName, godDescription, new DemeterPower(godPowerType, godPowerDescription), null);
            case "Hephaestus" -> new God(godName, godDescription, new HephaestusPower(godPowerType, godPowerDescription), null);
            case "Minotaur" -> new God(godName, godDescription, new MinotaurPower(godPowerType, godPowerDescription), null);
            case "Pan" -> new God(godName, godDescription, new PanPower(godPowerType, godPowerDescription), null);
            case "Prometheus" -> new God(godName, godDescription, new PrometheusPower(godPowerType, godPowerDescription), null);
            case "Chronus" -> new God(godName, godDescription, new ChronusPower(godPowerType, godPowerDescription), null);
            case "Hera" -> new God(godName, godDescription, new HeraPower(godPowerType, godPowerDescription), null);
            case "Hestia" -> new God(godName, godDescription, new HestiaPower(godPowerType, godPowerDescription), null);
            case "Zeus" -> new God(godName, godDescription, new ZeusPower(godPowerType, godPowerDescription), null);
            case "Triton" -> new God(godName, godDescription, new TritonPower(godPowerType, godPowerDescription), null);
            default -> null;
        };
    }



    /**
     * Return the selected {@link God god}.
     *
     * @param choice index
     * @return selectedGod god
     */
    public God getGod(int choice) {
        return this.get(choice);
    }

    public God getGodByName(String string) {

        for (God god : this) {
            if (god.getGodName().equals(string))
                return god;
        }

        return null;
    }



    //Print to screen all the gods inside the Deck
    @Override
    public String toString() {
        String string = "Vuota";
        for (int i = 0; i < this.size(); i++) {
            string = string.concat(this.getGod(i).toString() + "\n");
        }
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deck)) return false;
        if (!super.equals(o)) return false;

        Deck gods = (Deck) o;

        return Objects.equals(map, gods.map);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }
}
