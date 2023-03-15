package sai.ecommerce.model.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponse {
  private int productId;
  private String productName;
  private double price;
  private int quantity;
}
