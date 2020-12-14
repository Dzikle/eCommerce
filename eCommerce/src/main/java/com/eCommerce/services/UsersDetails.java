package com.eCommerce.services;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eCommerce.entity.RoleName;
import com.eCommerce.entity.User;


public class UsersDetails implements UserDetails{

	
	
	
	private User user;
	

	public UsersDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
        
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		RoleName role = user.getRole();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
       
            authorities.add(new SimpleGrantedAuthority(role.name()));
       
        return authorities;
	}
	
	

}
