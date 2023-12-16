package com.shawn.backendbase.service;

import com.shawn.backendbase.data.User;
import com.shawn.backendbase.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void addExampleUser() {
        final User user = new User();
        user.setUsername("nagi");
        user.setRole("buyer");
        user.setEmail("nagisemail@gmail.com");
        user.setPassword("nagispass");
        this.userRepository.save(user);
    }

    public User getUserByUsername(final String username) {
        if (this.userRepository.findByUsername(username).isEmpty()) {
            this.addExampleUser();
        }
        final Optional<User> user = this.userRepository.findByUsername(username);
        return user.get();
    }
}
