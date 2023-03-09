package sai.ecommerce.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sai.ecommerce.domain.User;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.LoginRequest;
import sai.ecommerce.model.LoginResponse;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.model.SignupResponse;
import sai.ecommerce.repository.UserRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public static final String USER_REGISTRATION_SUCCESS = "User registered in successfully";
  public static final String DUPLICATE_EMAIL = "User exits with provided email";

  @Transactional
  public SignupResponse registerUser(SignupRequest signupRequest) {
    Optional<User> optionalUser = userRepository.findByEmail(signupRequest.getEmail());
    if (optionalUser.isPresent()) {
      throw new BadRequestException(DUPLICATE_EMAIL);
    } else {
      User newUser =
          User.builder()
              .name(signupRequest.getName())
              .email(signupRequest.getEmail())
              .password(getPasswordHash(signupRequest.getPassword()))
              .build();
      userRepository.save(newUser);
      return new SignupResponse(USER_REGISTRATION_SUCCESS, newUser.getName(), newUser.getEmail());
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
