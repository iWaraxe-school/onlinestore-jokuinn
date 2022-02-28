package com.reut.store.comparator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class XmlReader {

    public Map<String, String> getAllPropertiesToSort() throws ParserConfigurationException, IOException, SAXException {
        String filePath = "store\\src\\main\\resources\\config.xml";
        String tag = "sort";
        Map<String, String> propertiesMap = new LinkedHashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(filePath);

        Node node = document.getElementsByTagName(tag).item(0);
        NodeList sortProperties = node.getChildNodes();

        Element element;
        for (int i = 0; i < sortProperties.getLength(); i++) {
            if (sortProperties.item(i).getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) sortProperties.item(i);

                propertiesMap.put(element.getTagName().toLowerCase(Locale.ROOT), element.getTextContent().toUpperCase(Locale.ROOT));
            }
        }

        return propertiesMap;
    }
}
