package com.sample.ecb.exchange.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sample.ecb.exchange.Output;

@Controller
public class MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	Map<String, Map<String, String>> parsedData = new HashMap<String, Map<String, String>>();

	void parseDocFromURL(String URL){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new URL(URL).openStream()));
			NodeList elementsList = doc.getElementsByTagName("Cube");
			for(int i=0; i<elementsList.getLength(); i++){
				Node outterElement = elementsList.item(i);
				if (outterElement.getAttributes().getNamedItem("time") != null){
					String date = outterElement.getAttributes().getNamedItem("time").getNodeValue();
					Map<String, String> rates = new HashMap<String, String>();
					NodeList childs = outterElement.getChildNodes();
					for(int k = 0;k < childs.getLength(); k++){
						Node innerElement = childs.item(k);
						NamedNodeMap attributes = innerElement.getAttributes();
						if (attributes != null){
							for(int j=0; j<attributes.getLength(); j++){
								rates.put(attributes.getNamedItem("currency").getNodeValue(),attributes.getNamedItem("rate").getNodeValue());
							}
						}
					}
					if (rates != null){
						parsedData.put(date, rates);	
					}
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			log.debug(e.getMessage());
		}
	} 

	@Async
	@Scheduled(fixedDelay=10000)
	private void parseRemoteXMLsJob(){
		parseDocFromURL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml");
		parseDocFromURL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
		System.out.println("XML parsing job executed");
	}

	@ResponseBody
	@RequestMapping("/currency/{currency}/date/{date}")
	public Output getResult(@PathVariable( "currency" ) String currency, @PathVariable( "date" ) String date  ) {
		String value=parsedData.get(date).get(currency);
		Output output = new Output("", "", "");
		if (value != null){
			output = new Output(currency, value, date);
		}
		return output;
	}

	@ResponseBody
	@RequestMapping("/currency/{currency}")
	public List<Output> getResultForCurrency(@PathVariable( "currency" ) String currency ) {
		List<Output> output = new ArrayList<Output>();
		for (String date: parsedData.keySet()) {
			String value = parsedData.get(date).get(currency);
			if (value != null){
				output.add(new Output(currency, value, date));
			}
		}
		return output;
	}

	@ResponseBody
	@RequestMapping("/date/{date}")
	public List<Output> getResultForDate(@PathVariable( "date" ) String date ) {
		List<Output> output = new ArrayList<Output>();
		Map<String, String> parsedDataForDate= parsedData.get(date);
		for (String currency: parsedDataForDate.keySet()) {
			String value = parsedDataForDate.get(currency);
			if (value != null){
				output.add(new Output(currency, value, date));
			}
		}
		return output;
	}   
}