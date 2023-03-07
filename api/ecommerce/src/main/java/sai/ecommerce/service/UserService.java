package sai.ecommerce.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.LoginRequest;
import sai.ecommerce.model.LoginResponse;
import sai.ecommerce.model.MessageResponse;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.repository.UserRepository;
import sai.ecommerce.utils.MessageConstants;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  @Transactional
  public ResponseEntity<MessageResponse> registerUser(SignupRequest signupRequest) {
    Optional<User> optionalUser = userRepository.findByEmail(signupRequest.getEmail());
    if (optionalUser.isPresent()) {
      logger.info(
          String.format("%s : %s", MessageConstants.DUPLICATE_EMAIL, signupRequest.getEmail()));
      return ResponseEntity.badRequest()
          .body(new MessageResponse(MessageConstants.DUPLICATE_EMAIL));

    } else {
      User newUser =
          User.builder()
              .name(signupRequest.getName())
              .email(signupRequest.getEmail())
              .password(getPasswordHash(signupRequest.getPassword()))
              .build();
      userRepository.save(newUser);
      return ResponseEntity.ok()
          .body(new MessageResponse(MessageConstants.USER_REGISTRATION_SUCCESS));
    }
  }

  public LoginResponse authenticateUser(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    final UserDetails user = (User) authentication.getPrincipal();

    return new LoginResponse(jwtService.generateJwtToken(user), user.getUsername());
  }

  private String getPasswordHash(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }
}
