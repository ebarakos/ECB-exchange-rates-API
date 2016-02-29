package com.sample.ecb.exchange;

public class Currency {

    private String name;
    private double value;
    
    public Currency(String name, double d) {
		super();
		this.name = name;
		this.value = d;
	}
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
}