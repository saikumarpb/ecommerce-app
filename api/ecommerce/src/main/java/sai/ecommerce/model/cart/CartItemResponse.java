package sai.ecommerce.model.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sai.ecommerce.domain.CartItem;
import sai.ecommerce.model.product.ProductResponse;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {
  private int productId;
  private String productName;
  private double price;
  private int quantity;

  public static List<CartItemResponse> fromList(List<CartItem> cartItemList) {
    // cartItemList will be null for a new user
    if (cartItemList == null) {
      return new ArrayList<>();
    }
    return cartItemList.stream().map(CartItemResponse::from).collect(Collectors.toList());
  }

  public static CartItemResponse from(CartItem cartItem) {
    ProductResponse product = ProductResponse.from(cartItem.getProduct());
    return new CartItemResponse(
        product.getId(), product.getName(), product.getPrice(), cartItem.getQuantity());
  }
}
