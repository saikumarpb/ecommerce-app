package sai.ecommerce.model.cart;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartRequest {
  @NotNull private int productId;

  @NotNull @PositiveOrZero private int quantity;
}
