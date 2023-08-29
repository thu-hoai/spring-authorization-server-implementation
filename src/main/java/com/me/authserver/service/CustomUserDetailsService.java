package com.me.authserver.service;

import com.example.demo.authservice.entity.Role;
import com.example.demo.authservice.entity.UserEntity;
import com.example.demo.authservice.model.JwtUser;
import com.example.demo.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<UserEntity> optional = userRepository.findByUsername(username);
    if (optional.isEmpty()) {
      throw new UsernameNotFoundException("No user found for " + username);
    }

    LOGGER.info("Found user for {}", username);
    JwtUser jwtUser = new JwtUser();
    jwtUser.setUsername(username);
    jwtUser.setRoles(
        optional.get().getAuthorities().stream().map(Role::getName).collect(Collectors.toSet()));
    jwtUser.setAuthorities(optional.get().getAuthorities());
    jwtUser.setEmail(optional.get().getEmail());
    jwtUser.setFirstName(optional.get().getFirstName());
    jwtUser.setLastName(optional.get().getLastName());
    jwtUser.setPassword(optional.get().getPassword());

    return jwtUser;
  }
}
