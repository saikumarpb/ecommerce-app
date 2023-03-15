package sai.ecommerce.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.utils.MockData;
import sai.ecommerce.utils.TestUtils;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartServiceTests {
  @Autowired private CartService cartService;
  @Autowired private TestUtils testUtils;

  private User user;

  @BeforeAll
  public void initialize() throws Exception {
    testUtils.clean();
    user = testUtils.getUser();
  }

  @Test
  @Order(1)
  @DisplayName("Test empty cart")
  void testGetEmptyCart() {
    List<CartItemResponse> cartItemResponseList = cartService.getUserCart(user);
    Assertions.assertEquals(0, cartItemResponseList.size());
  }

  @Test
  @Order(2)
  @DisplayName("Failure test : Add new product to cart with zero quantity")
  void testAddZeroQuantity() {
    try {
      cartService.updateUserCart(user, MockData.getUpdateCartRequest(1, 0));
    } catch (Exception e) {
      Assertions.assertEquals("Invalid operation", e.getMessage());
    }
  }

  @Test
  @Order(3)
  @DisplayName("Add valid product to cart")
  void testUpdateUserCart() {
    List<CartItemResponse> cartItemResponseList =
        cartService.updateUserCart(user, MockData.getUpdateCartRequest(1, 1));
    Assertions.assertTrue(cartItemResponseList.size() > 0);
  }

  @Test
  @Order(4)
  @DisplayName("Test non empty cart")
  void testGetLoadedCart() {
    List<CartItemResponse> cartItemResponseList = cartService.getUserCart(user);
    Assertions.assertTrue(cartItemResponseList.size() > 0);
  }

  @Test
  @Order(5)
  @DisplayName("Failure test : Add invalid product to cart")
  void testAddInvalidProduct() {
    try {
      cartService.updateUserCart(user, MockData.getUpdateCartRequest(123, 1));
    } catch (Exception e) {
      Assertions.assertEquals("Product not found", e.getMessage());
    }
  }

  @Test
  @Order(6)
  @DisplayName("Test delete product from cart")
  void testDeleteProduct() {
    List<CartItemResponse> cartItemResponseList =
        cartService.updateUserCart(user, MockData.getUpdateCartRequest(1, 0));
    Assertions.assertEquals(0, cartItemResponseList.size());
  }
}
