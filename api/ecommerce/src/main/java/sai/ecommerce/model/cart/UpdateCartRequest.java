package sai.ecommerce.model.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartRequest {
  private int productId;
  private int quantity;
}
