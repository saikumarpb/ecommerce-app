package sai.ecommerce.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sai.ecommerce.model.LoginRequest;
import sai.ecommerce.model.LoginResponse;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.model.SignupResponse;
import sai.ecommerce.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthController {
  private final UserService userService;

  @PostMapping("/signup")
  public SignupResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
    return userService.registerUser(signupRequest);
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    return userService.authenticateUser(loginRequest);
  }
}
