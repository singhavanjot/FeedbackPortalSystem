package com.Portal.FeedbackPortal.service;

import com.Portal.FeedbackPortal.model.User;
import com.Portal.FeedbackPortal.repository.UserRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; // To encrypt passwords

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// Register a new user after encrypting the password
	public User registerUser(User user) {
		// perform checks if user/email already exists (optional)
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	// Find user by username for login/authentication
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}

