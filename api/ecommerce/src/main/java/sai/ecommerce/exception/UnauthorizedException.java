package sai.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException() {
    super("UnAuthorizedAccessException: Access Denied");
  }

  public UnauthorizedException(String message) {
    super(message);
  }
}
