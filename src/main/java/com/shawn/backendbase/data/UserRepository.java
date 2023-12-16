package com.shawn.backendbase.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(final String username);

    Optional<User> findByEmail(final String email);
}
