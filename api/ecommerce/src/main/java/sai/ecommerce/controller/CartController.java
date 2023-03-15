package sai.ecommerce.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.model.cart.UpdateCartRequest;
import sai.ecommerce.service.AuthService;
import sai.ecommerce.service.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {
  private final CartService cartService;
  private final AuthService authService;

  @GetMapping
  public List<CartItemResponse> getUserCart() {
    int userId = authService.getUserId();
    return cartService.getUserCart(userId);
  }

  @PutMapping("/update")
  public List<CartItemResponse> updateUserCart(@RequestBody UpdateCartRequest updateCartRequest) {
    int userId = authService.getUserId();
    return cartService.updateUserCart(userId, updateCartRequest);
  }
}
