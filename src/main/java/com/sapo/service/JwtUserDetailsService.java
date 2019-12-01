package com.sapo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sapo.dto.UserRegisterDTO;
import com.sapo.model.User;
import com.sapo.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
	public User save(UserRegisterDTO user) {
			User newUser = new User();
			newUser.setPhone(user.getPhone());
			newUser.setEmail(user.getEmail());
			newUser.setUsername(user.getUsername());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			return userRepository.save(newUser);
	}
	public User save(User user) {
		User newUser = new User();
		newUser.setPhone(user.getPhone());
		newUser.setEmail(user.getEmail());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(newUser);

	}
	


}