package com.eCommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

public enum Color {
	 BLACK("Black"), 
	    BLUE("Blue"), 
	    RED("Red"), 
	    YELLOW("Yellow"), 
	    GREEN("Green"),
	    ORANGE("Orange"), 
	    PURPLE("Purple"), 
	    WHITE("White");
	    
	    private final String displayValue;
	    
	    private Color(String displayValue) {
	        this.displayValue = displayValue;
	    }
	    
	    public String getDisplayValue() {
	        return displayValue;
	    }
}
