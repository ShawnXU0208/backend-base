package com.shawn.backendbase.data;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByAccountName(final String accountName);

  Optional<User> findByEmail(final String email);
}
