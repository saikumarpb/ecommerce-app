package sai.ecommerce.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sai.ecommerce.model.LoginRequest;
import sai.ecommerce.model.LoginResponse;
import sai.ecommerce.model.MessageResponse;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthController {
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(
      @Valid @RequestBody SignupRequest signupRequest) {
    return userService.registerUser(signupRequest);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {
    LoginResponse loginResponse = userService.authenticateUser(loginRequest);
    return ResponseEntity.ok().body(loginResponse);
  }
}
