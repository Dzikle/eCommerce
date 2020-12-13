package com.eCommerce;

import java.time.LocalDate;
import java.util.List;

import com.eCommerce.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private String email;
	
	private String password;
	
    private String firstName;
	
	private String lastName;
	
	private List<Address> address;
	
	

}
