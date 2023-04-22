package com.yash.ems.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.yash.ems.config.JwtUtils;
import com.yash.ems.exception.UserNotFoundException;
import com.yash.ems.model.JwtRequest;
import com.yash.ems.model.JwtResponse;
import com.yash.ems.model.User;
import com.yash.ems.service.impl.UserDetailsServiceImpl;
import java.security.Principal;

/**
 * AuthenticateController interact with service layer to complete login the work
 * according to web request.
 *
 * @author Shubham Bhake
 * @since 28-2-2023
 */

@RestController
@CrossOrigin("*")
public class AuthenticateController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * This controller method handles the HTTP Post request for generate token
	 * matching with the given URI.
	 * 
	 * @RequestBody JwtRequest jwtRequest
	 */
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		try {

			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
          
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found ");
		}

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtils.generateToken(userDetails);

		LOGGER.info("token genrated");
		return ResponseEntity.ok(new JwtResponse(token));

	}

	private void authenticate(String username, String password) throws Exception {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {
			throw new Exception("USER DISABLED " + e.getMessage());
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials " + e.getMessage());
		}
	}

	/**
	 * This controller method handles the HTTP Get request for to return user with
	 * generate token matching with the given URI.
	 * 
	 * 
	 */
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));

	}

}
