package sai.ecommerce.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
  @NotBlank
  @Size(min = 1, max = 100)
  private String name;

  @NotBlank private String email;

  @NotBlank private String password;
}
