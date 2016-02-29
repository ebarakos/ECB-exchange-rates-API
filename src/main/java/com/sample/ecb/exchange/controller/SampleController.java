package com.sample.ecb.exchange.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.ecb.exchange.Currency;
import com.sample.ecb.exchange.XMLTest;

@Controller
public class SampleController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping("/api/hello")
    public String helloWorld() {
        log.debug("Logging works!");
        return "Hello World!";
    }
    
	Map<String,Currency> currencies = new HashMap<>();	

    @ResponseBody
    @RequestMapping("/{name}")
    public Currency greeting(@PathVariable( "name" ) String name ) {
    	
    	XMLTest test = new XMLTest();

//        System.out.println(name);
    	currencies.put("eur",new Currency("EUR", 10.8));
    	currencies.put("chf",new Currency("CHF", 9.9));
        return currencies.get(name);
    }
    
}

