package com.yash.ems.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.yash.ems.controller.AuthenticateController;
import com.yash.ems.controller.EmployeeController;
import com.yash.ems.exception.UserFoundException;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;
import com.yash.ems.repo.RoleRepository;
import com.yash.ems.repo.UserRepository;
import com.yash.ems.services.UserService;
import java.util.Set;

/**
 * UserServiceImpl service interact with repository layer to complete Singup the
 * work according to web request.
 *
 * @author Shubham Bhake
 * @since 01-03-2023
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	/**
	 * This service method handles the HTTP Post request for insert/ register the
	 * user and their role, matching with the given URI.
	 * 
	 */
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {

		User local = this.userRepository.findByUsername(user.getUsername());
		if (local != null) {

			LOGGER.info("User is alredy exixting ");

			throw new UserFoundException();
		} else {

			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);

			LOGGER.info("User Created");
		}

		return local;
	}

	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
	}

}
