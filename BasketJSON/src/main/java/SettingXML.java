package BasketJSON.src.main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SettingXML {
    final boolean isload;
    final String loadFile;
    final String loadFormat;

    final boolean isSave;
    final String saveFile;
    final String saveFormat;



    final boolean isLog;
    final String logFile;


    public SettingXML(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        Element loadSettings = (Element) root.getElementsByTagName("load").item(0);
        Element saveSettings = (Element) root.getElementsByTagName("save").item(0);
        Element logSettings = (Element) root.getElementsByTagName("log").item(0);

        isload = Boolean.parseBoolean(loadSettings.getElementsByTagName("enabled").item(0).getTextContent());
        loadFile = loadSettings.getElementsByTagName("fileName").item(0).getTextContent();
        loadFormat = loadSettings.getElementsByTagName("format").item(0).getTextContent();

        isSave = Boolean.parseBoolean(saveSettings.getElementsByTagName("enabled").item(0).getTextContent());
        saveFile = saveSettings.getElementsByTagName("fileName").item(0).getTextContent();
        saveFormat = saveSettings.getElementsByTagName("format").item(0).getTextContent();

        isLog = Boolean.parseBoolean(logSettings.getElementsByTagName("enabled").item(0).getTextContent());
        logFile = logSettings.getElementsByTagName("fileName").item(0).getTextContent();
    }
}
