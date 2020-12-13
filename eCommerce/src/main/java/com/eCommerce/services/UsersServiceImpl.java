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

import com.eCommerce.entity.Role;
import com.eCommerce.entity.User;
import com.eCommerce.repository.RoleRepository;
import com.eCommerce.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;


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

	
	
	



	@Override
	public User save(User userDto) {
		
		//Role role = new Role(2,RoleName.ROLE_USER);
		
		
		User user = new User(userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()),userDto.getFirstName(),userDto.getLastName(),  null, Arrays.asList
				(new Role("ROLE_USER")));
		
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
