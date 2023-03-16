package sai.ecommerce.model.cart;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCartRequest {
  @NotNull private int productId;

  @NotNull @PositiveOrZero private int quantity;
}
