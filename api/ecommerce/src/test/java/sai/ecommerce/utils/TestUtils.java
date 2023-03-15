package sai.ecommerce.utils;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sai.ecommerce.domain.User;
import sai.ecommerce.repository.CartItemRepository;
import sai.ecommerce.repository.CartRepository;
import sai.ecommerce.repository.UserRepository;
import sai.ecommerce.service.UserService;

@Component
public class TestUtils {
  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private CartRepository cartRepository;
  @Autowired private CartItemRepository cartItemRepository;

  public User getUser() throws Exception {
    Optional<User> existingUser = userRepository.findByEmail(MockData.userEmail);
    if (existingUser.isPresent()) {
      return existingUser.get();
    } else {
      userService.registerUser(MockData.getSignupRequest());
      return userRepository
          .findByEmail(MockData.userEmail)
          .orElseThrow(() -> new Exception("User creation failed"));
    }
  }

  public void clean() {
    cartRepository.deleteAll();
    cartItemRepository.deleteAll();
  }
}
