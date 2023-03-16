package sai.ecommerce.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Cart;
import sai.ecommerce.domain.CartItem;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.User;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.model.cart.UpdateCartRequest;
import sai.ecommerce.repository.CartItemRepository;
import sai.ecommerce.repository.CartRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductService productService;

  public List<CartItemResponse> getUserCart(User user) {
    Cart userCart = cartRepository.findByUserId(user.getId()).orElseGet(() -> createNewCart(user));
    return CartItemResponse.fromList(userCart.getCartItems());
  }

  public List<CartItemResponse> updateUserCart(User user, UpdateCartRequest updateCartRequest) {
    int quantity = updateCartRequest.getQuantity();
    Product product = productService.getProduct(updateCartRequest.getProductId());

    validateQuantity(product, quantity);

    Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> createNewCart(user));
    Optional<CartItem> existingCartItem =
        cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

    if (quantity == 0) {
      if (existingCartItem.isPresent()) {
        cartItemRepository.deleteById(existingCartItem.get().getId());
      } else {
        throw new BadRequestException("Invalid operation");
      }
    } else {
      CartItem cartItem = existingCartItem.orElseGet(() -> new CartItem(cart, product, quantity));
      cartItem.setQuantity(quantity);
      cartItemRepository.save(cartItem);
    }

    List<CartItem> cartItems = cartRepository.findByUserId(user.getId()).get().getCartItems();
    return CartItemResponse.fromList(cartItems);
  }

  private void validateQuantity(Product product, int quantity) {
    int availableQuantity = product.getStock();
    if (quantity > availableQuantity) {
      throw new BadRequestException(
          String.format("Quantity exceeded, Available items in stock : %s", availableQuantity));
    }
  }

  private Cart createNewCart(User user) {
    Cart newCart = new Cart();
    newCart.setUser(user);
    cartRepository.save(newCart);
    return cartRepository.findByUserId(user.getId()).get();
  }
}
