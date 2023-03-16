package sai.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sai.ecommerce.domain.Cart;
import sai.ecommerce.domain.CartItem;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.model.cart.UpdateCartRequest;
import sai.ecommerce.repository.CartItemRepository;
import sai.ecommerce.repository.CartRepository;
import sai.ecommerce.repository.ProductRepository;
import sai.ecommerce.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartServiceTests {
  @Autowired private CartService cartService;
  @Autowired private UserService userService;
  @Autowired private CartRepository cartRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private CartItemRepository cartItemRepository;
  @Autowired private ProductRepository productRepository;

  private User user;

  @BeforeEach
  public void setup() {
    String userName = "test user";
    String userEmail = "cart@test.com";
    String userPassword = "testPass@123";
    userService.registerUser(new SignupRequest(userName, userEmail, userPassword));
    user = userRepository.findByEmail(userEmail).get();
  }

  @AfterEach
  public void cleanup() {
    cartItemRepository.deleteAll();
    cartRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("Test non empty cart")
  void getUserCart_nonEmptyCart() {
    saveCartItem(user, 1, 1);

    List<CartItemResponse> cartItemResponseList = cartService.getUserCart(user);
    assertEquals(1, cartItemResponseList.size());
  }

  @Test
  @DisplayName("Add valid product to cart")
  void updateUserCart_success() {
    List<CartItemResponse> cartItemResponseList =
        cartService.updateUserCart(user, new UpdateCartRequest(1, 1));
    assertEquals(1, cartItemResponseList.size());
  }

  @Test
  @DisplayName("Add new product to cart with zero quantity")
  void updateUserCart_invalidQuantity() {
    try {
      cartService.updateUserCart(user, new UpdateCartRequest(1, 0));
    } catch (Exception e) {
      assertEquals("Invalid operation", e.getMessage());
    }
  }

  @Test
  @DisplayName("Add invalid product to cart")
  void updateUserCart_invalidProduct() {
    try {
      cartService.updateUserCart(user, new UpdateCartRequest(123, 1));
    } catch (Exception e) {
      assertEquals("Product not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Delete product from cart")
  void updateUserCart_deleteProduct() {
    saveCartItem(user, 1, 1);

    List<CartItemResponse> cartItemResponseList =
        cartService.updateUserCart(user, new UpdateCartRequest(1, 0));
    assertEquals(0, cartItemResponseList.size());
  }

  private void saveCartItem(User user, int productId, int quantity) {
    Cart cart =
        cartRepository.findByUserId(user.getId()).orElseGet(() -> cartService.createNewCart(user));
    Product product = productRepository.findById(productId).get();
    cartItemRepository.save(new CartItem(cart, product, quantity));
  }
}
