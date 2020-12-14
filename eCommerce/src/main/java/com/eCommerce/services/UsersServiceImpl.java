package com.eCommerce.services;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eCommerce.entity.RoleName;
import com.eCommerce.entity.ShoppingCart;
import com.eCommerce.entity.User;
import com.eCommerce.repository.RoleRepository;
import com.eCommerce.repository.ShoppingCartRepository;
import com.eCommerce.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;


@Service
@Transactional
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ShoppingCartRepository cartRepository;

	
	
	



	@Override
	public User save(User userDto) {
		
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(RoleName.ROLE_USER);
        user.setAddress(userDto.getAddress());
		
		return userRepository.save(user);
	}
	
	



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("You are not signUped with that email");
		}
		
		return new UsersDetails(user);
	}
	
	



	@Override
	public Page<User> findPagina(Integer pageNumber, Integer pageSize, String sortField, String sortDirection) {
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		
		return userRepository.findAll(pageable);
	}
	
	

}
