package it.polimi.ingsw.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Deck {

    private static Deck istance=null;
    private List<God> gods;

    private Deck() {
        gods = new ArrayList<>();
        loadXML();
    }

    public static Deck getIstance() {
        if(istance==null)
            istance = new Deck();
        return istance;
    }

    private boolean loadXML(){
        File file = new File("src/main/java/it/polimi/ingsw/Model/Source/gods.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("god");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element eElement = (Element) nodeList.item(i);

                String godName = eElement.getElementsByTagName("name").item(0).getTextContent();
                String godDescription = eElement.getElementsByTagName("description").item(0).getTextContent();
                String godPowerType = eElement.getElementsByTagName("powerType").item(0).getTextContent();
                String godPowerDescription = eElement.getElementsByTagName("powerDescription").item(0).getTextContent();

                God tmp = new God(godName, godDescription, new Power(godPowerType, godPowerDescription));

                gods.add(tmp);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeGod(God selectedGod) {
        gods.remove(selectedGod);
    }

    // public void initDeck() { };  // -> default constructor is the new initDeck()


}
