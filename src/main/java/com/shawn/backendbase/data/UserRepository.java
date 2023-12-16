package com.shawn.backendbase.data;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(final String username);

  Optional<User> findByEmail(final String email);
}
