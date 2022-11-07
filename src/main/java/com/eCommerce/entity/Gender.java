package com.eCommerce.entity;


public enum Gender {
	 	MALE("Male"), 
	    FEMALE("Female"), 
	    UNISEX("Unisex");
	   
	    
	    private final String displayValue;
	    
	    private Gender(String displayValue) {
	        this.displayValue = displayValue;
	    }
	    
	    public String getDisplayValue() {
	        return displayValue;
	    }
}
