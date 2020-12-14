package com.eCommerce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public enum Category {
	
		TSHIRT("T-Shirt"), 
	    UNDERPANTS("Underpants"), 
	    BLOUSE("Blouse"), 
	    COAT("Coat"), 
	    JACKET("Jacket"),
	    DRESS("Dress"), 
	    SUIT("Suit"), 
	    SKIRT("Skirt"), 
	    UNDERSHIRT("Undershirt"), 
	    ACCESSORIES("Accessories"),
	    SWIMSUIT("Swimsuit");
	    
	    private final String displayValue;
	    
	    private Category(String displayValue) {
	        this.displayValue = displayValue;
	    }
	    
	    public String getDisplayValue() {
	        return displayValue;
	    }

}
