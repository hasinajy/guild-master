package utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import database.Configuration;
import exceptions.ConfigurationLoadException;

public class ConfigLoader extends Utility {
    public static Configuration loadConfiguration(String configFilePath) throws ConfigurationLoadException {
        try {
            File file = new File(configFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Prevent XXE attacks by disabling external entity access
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbFactory.setXIncludeAware(false);
            dbFactory.setExpandEntityReferences(false);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("db-config");

            String databaseUrl = null;
            String username = null;
            String password = null;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    databaseUrl = element.getElementsByTagName("database-url").item(0).getTextContent();
                    username = element.getElementsByTagName("username").item(0).getTextContent();
                    password = element.getElementsByTagName("password").item(0).getTextContent();
                }
            }

            return new Configuration(databaseUrl, username, password);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConfigurationLoadException("Error loading configuration from " + configFilePath, e);
        }
    }
}
