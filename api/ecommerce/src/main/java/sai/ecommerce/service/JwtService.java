package sai.ecommerce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sai.ecommerce.exception.BadRequestException;

@Service
@Slf4j
public class JwtService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiry-in-ms}")
  private Long jwtExpiryInMillis;

  public String generateJwtToken(UserDetails userDetails) {

    JWTCreator.Builder builder =
        JWT.create()
            .withSubject(userDetails.getUsername())
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withExpiresAt(Date.from(Instant.now().plusMillis(jwtExpiryInMillis)));

    return builder.sign(Algorithm.HMAC256(jwtSecret));
  }

  public boolean isTokenExpired(String token) {
    return JWT.decode(token).getExpiresAt().before(new Date());
  }

  public DecodedJWT decodeJWT(String authToken) {
    try {
      return JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(authToken);
    } catch (SignatureVerificationException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
      throw new SignatureVerificationException(Algorithm.HMAC256(jwtSecret));
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new BadRequestException();
    }
  }
}
