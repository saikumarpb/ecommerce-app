package sai.ecommerce.exception;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException() {
    super("UnAuthorizedAccessException: Access Denied");
  }

  public UnauthorizedException(String message) {
    super(message);
  }
}
