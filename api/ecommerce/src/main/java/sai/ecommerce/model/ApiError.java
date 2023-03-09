package sai.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
  private int status;
  private String error;
  private String message;
}
