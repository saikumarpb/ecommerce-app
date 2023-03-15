package sai.ecommerce.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.model.cart.UpdateCartRequest;
import sai.ecommerce.service.CartService;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {
  private final CartService cartService;

  @GetMapping
  public List<CartItemResponse> getUserCart(@AuthenticationPrincipal User user) {
    return cartService.getUserCart(user);
  }

  @PutMapping
  public List<CartItemResponse> updateUserCart(
      @AuthenticationPrincipal User user, @Valid @RequestBody UpdateCartRequest updateCartRequest) {
    return cartService.updateUserCart(user, updateCartRequest);
  }
}
