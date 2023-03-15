package sai.ecommerce.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.User;

@Service
public class AuthService {
  public int getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    return user.getId();
  }
}
