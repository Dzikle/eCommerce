package com.eCommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


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
