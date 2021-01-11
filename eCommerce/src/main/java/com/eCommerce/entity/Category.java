package com.eCommerce.entity;



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
