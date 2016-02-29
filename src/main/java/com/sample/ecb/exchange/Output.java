package com.sample.ecb.exchange;

public class Output{

    private String name;
    private String value;
    private String date;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Output(String name, String value, String date) {
		super();
		this.name = name;
		this.value = value;
		this.date = date;
	}
	
}