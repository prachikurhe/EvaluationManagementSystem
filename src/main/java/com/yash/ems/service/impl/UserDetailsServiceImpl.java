package com.yash.ems.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.yash.ems.model.User;
import com.yash.ems.repo.UserRepository;

/**
 * UserDetailsServiceImpl service interact with repository layer to complete
 * Login work according to web request.
 *
 * @author Shubham Bhake
 * @since 01-03-2023
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

//    private UserRepository userRepository;
	@Autowired
	public UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = this.userRepository.findByUsername(username);

		User user = userRepository.findByUsername(username);
		if (user == null) {

			LOGGER.info("User not found");

			throw new UsernameNotFoundException("No user found !!");
		}

		return user;
	}
}
