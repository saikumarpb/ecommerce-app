package sai.ecommerce.utils;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sai.ecommerce.domain.User;
import sai.ecommerce.repository.UserRepository;
import sai.ecommerce.service.UserService;

@Component
public class TestUtils {
  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;

  public User getUser(String email) throws Exception {
    Optional<User> existingUser = userRepository.findByEmail(MockData.userEmail);
    if (existingUser.isPresent()) {
      return existingUser.get();
    } else {
      userService.registerUser(MockData.getSignupRequest(email));
      return userRepository
          .findByEmail(MockData.userEmail)
          .orElseThrow(() -> new Exception("User creation failed"));
    }
  }
}
