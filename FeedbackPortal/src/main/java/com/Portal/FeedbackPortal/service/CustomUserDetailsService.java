package com.Portal.FeedbackPortal.service;

import com.Portal.FeedbackPortal.model.User;
import com.Portal.FeedbackPortal.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usr = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Roles already in "ROLE_USER", "ROLE_ADMIN" format
        String[] authorities = usr.getRoles().toArray(new String[0]);

        return org.springframework.security.core.userdetails.User.builder()
                .username(usr.getUsername())
                .password(usr.getPassword())
                .authorities(authorities)
                .build();
    }

}
