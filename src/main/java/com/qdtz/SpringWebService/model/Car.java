package com.qdtz.SpringWebService.model;

public class Car {
	
	private String name;
	private String clor;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClor() {
		return clor;
	}
	public void setClor(String clor) {
		this.clor = clor;
	}
	
	@Override
	public String toString() {
		return "Car [name=" + name + ", clor=" + clor + "]";
	}
	
	

}
