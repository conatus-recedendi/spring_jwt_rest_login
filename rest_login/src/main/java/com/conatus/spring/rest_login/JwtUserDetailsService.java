package com.conatus.spring.rest_login;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.conatus.spring.rest_login.UserDAO;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntityClass user = userDAO.findByUsername(username);
		if (user != null) {
			return new User(user.getUsername(), user.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	public UserDAO save(UserDTO user) {
		UserEntityClass newUser = new UserEntityClass();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		//newUser.setToken(user.getToken());
		//newUser.setAccessToken(user.getAccessToken());
		return userDAO.save(newUser);
	}
}