package com.shawn.backendbase.service.auth;

import com.shawn.backendbase.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The implementation {@link org.springframework.security.core.userdetails.UserDetailsService}.
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByAccountName(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username '%s' doesn't exist", username)));
  }

}
