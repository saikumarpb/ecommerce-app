package sai.ecommerce.service;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sai.ecommerce.domain.Cart;
import sai.ecommerce.domain.CartItem;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.repository.CartItemRepository;
import sai.ecommerce.repository.CartRepository;
import sai.ecommerce.repository.ProductRepository;
import sai.ecommerce.utils.MockData;
import sai.ecommerce.utils.TestUtils;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartServiceTests {
  @Autowired private CartService cartService;
  @Autowired private TestUtils testUtils;
  @Autowired private CartRepository cartRepository;
  @Autowired private CartItemRepository cartItemRepository;
  @Autowired private ProductRepository productRepository;

  private User user;

  @BeforeAll
  public void setupUser() throws Exception {
    user = testUtils.getUser(MockData.userEmail);
  }

  @AfterEach
  public void cleanup() {
    cartRepository.deleteAll();
    cartItemRepository.deleteAll();
  }

  @Test
  @DisplayName("Test empty cart")
  void getUserCart_emptyCart() {
    List<CartItemResponse> cartItemResponseList = cartService.getUserCart(user);
    Assertions.assertEquals(0, cartItemResponseList.size());
  }

  @Test
  @DisplayName("Test non empty cart")
  void getUserCart_nonEmptyCart() {
    saveCartItem(user, 1, 1);

    List<CartItemResponse> cartItemResponseList = cartService.getUserCart(user);
    Assertions.assertTrue(cartItemResponseList.size() > 0);
  }

  @Test
  @DisplayName("Add valid product to cart")
  void updateUserCart_success() {
    List<CartItemResponse> cartItemResponseList =
        cartService.updateUserCart(user, MockData.getUpdateCartRequest(1, 1));
    Assertions.assertTrue(cartItemResponseList.size() > 0);
  }

  @Test
  @DisplayName("Add new product to cart with zero quantity")
  void updateUserCart_invalidQuantity() {
    try {
      cartService.updateUserCart(user, MockData.getUpdateCartRequest(1, 0));
    } catch (Exception e) {
      Assertions.assertEquals("Invalid operation", e.getMessage());
    }
  }

  @Test
  @DisplayName("Add invalid product to cart")
  void updateUserCart_invalidProduct() {
    try {
      cartService.updateUserCart(user, MockData.getUpdateCartRequest(123, 1));
    } catch (Exception e) {
      Assertions.assertEquals("Product not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Delete product from cart")
  void updateUserCart_deleteProduct() {
    saveCartItem(user, 1, 1);

    List<CartItemResponse> cartItemResponseList =
        cartService.updateUserCart(user, MockData.getUpdateCartRequest(1, 0));
    Assertions.assertEquals(0, cartItemResponseList.size());
  }

  private Cart createCart(User user) {
    Cart cart = new Cart();
    cart.setUser(user);
    return cartRepository.save(cart);
  }

  private void saveCartItem(User user, int productId, int quantity) {
    Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> createCart(user));
    Product product = productRepository.findById(productId).get();
    cartItemRepository.save(new CartItem(cart, product, quantity));
  }
}
