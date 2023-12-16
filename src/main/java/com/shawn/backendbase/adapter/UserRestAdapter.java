package com.shawn.backendbase.adapter;

import com.shawn.backendbase.data.User;
import com.shawn.backendbase.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserRestAdapter {

  private final UserService userService;

  @Autowired
  public UserRestAdapter(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable final String username) {
    System.out.println(username);
    final User user = this.userService.getUserByUsername(username);
    return ResponseEntity.of(Optional.of(user));
  }
}
