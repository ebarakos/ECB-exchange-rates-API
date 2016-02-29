package com.sample.ecb.exchange;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLTest {
     {
        Map<String, Map<String, String>> parsedData = new HashMap<String, Map<String, String>>();

        try {
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          	DocumentBuilder builder = factory.newDocumentBuilder();
            Document history = builder.parse(new InputSource(new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml").openStream()));
            NodeList elementsList = history.getElementsByTagName("Cube");

            for(int i=0; i<elementsList.getLength(); i++){
            	Node outterElement = elementsList.item(i);
            	if (outterElement.getAttributes().getNamedItem("time") != null){
            		String date = outterElement.getAttributes().getNamedItem("time").getNodeValue();
//            		System.out.println(date);
        			Map<String, String> rates = new HashMap<String, String>();

            		NodeList childs = outterElement.getChildNodes();
            		
            		for(int k = 0;k < childs.getLength(); k++){
                    	Node innerElement = childs.item(k);
//                    	System.out.println(element);
            			NamedNodeMap attributes = innerElement.getAttributes();
            			for(int j=0; j<attributes.getLength(); j++){
            				rates.put(attributes.getNamedItem("currency").getNodeValue(),attributes.getNamedItem("rate").getNodeValue());
            			}
            		
            		}
            		if (rates != null){
            			parsedData.put(date, rates);	
            		}
            	}
            }
            System.out.println(parsedData.get("2016-02-24").get("BGN"));

            System.out.println("HERE!");
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        } catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
    }
}