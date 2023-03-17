package sai.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.State;
import sai.ecommerce.domain.User;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.model.cart.UpdateCartRequest;
import sai.ecommerce.model.order.OrderRequest;
import sai.ecommerce.model.order.OrderResponse;
import sai.ecommerce.repository.AddressRepository;
import sai.ecommerce.repository.CartItemRepository;
import sai.ecommerce.repository.CartRepository;
import sai.ecommerce.repository.OrderItemRepository;
import sai.ecommerce.repository.OrderRepository;
import sai.ecommerce.repository.ProductRepository;
import sai.ecommerce.repository.StateRepository;
import sai.ecommerce.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {
  @Autowired private OrderService orderService;
  @Autowired private AddressRepository addressRepository;
  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private StateRepository stateRepository;
  @Autowired private OrderRepository orderRepository;
  @Autowired private OrderItemRepository orderItemRepository;
  @Autowired private CartService cartService;
  @Autowired private CartRepository cartRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private CartItemRepository cartItemRepository;

  private User user1;
  private User user2;
  private Address address1;
  private Address address2;

  @BeforeEach
  public void setup() {
    String userName = "test user 1";

    String user1Email = "user1@test.com";
    String user2Email = "user2@test.com";

    String userPassword = "testPass@123";
    user1 = registerAndGetUser(userName, user1Email, userPassword);
    user2 = registerAndGetUser(userName, user2Email, userPassword);

    address1 = addressRepository.save(getMockAddress(user1, 1));
    address2 = addressRepository.save(getMockAddress(user2, 2));

    cartService.updateUserCart(user1, new UpdateCartRequest(1, 1));
  }

  @AfterEach
  public void cleanup() {
    orderItemRepository.deleteAll();
    orderRepository.deleteAll();

    cartItemRepository.deleteAll();
    cartRepository.deleteAll();

    addressRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("Create order")
  void placeOrder_success() {
    OrderResponse orderResponse =
        orderService.createOrder(user1, new OrderRequest(address1.getId()));

    assertEquals(1, orderResponse.getProducts().get(0).getProductId());
    assertEquals(1, orderResponse.getProducts().get(0).getQuantity());
  }

  @Test
  @DisplayName("Place order unauthorized address")
  void placeOrder_unauthorizedAddress() {
    try {
      orderService.createOrder(user2, new OrderRequest(address1.getId()));
    } catch (Exception e) {
      assertEquals("User is not authorized to access this address", e.getMessage());
    }
  }

  @Test
  @DisplayName("Place order for out of stock item")
  void placeOrder_outOfStockItem() {
    Product product = productRepository.findById(3).get();
    try {
      cartService.updateUserCart(user1, new UpdateCartRequest(3, product.getStock()));
      cartService.updateUserCart(user2, new UpdateCartRequest(3, 1));
      // user1 orders whole stock for product 3
      orderService.createOrder(user1, new OrderRequest(address1.getId()));
      // user2 tries to place order for product 3
      orderService.createOrder(user2, new OrderRequest(address2.getId()));
    } catch (BadRequestException e) {
      assertEquals(
          String.format("Quantity exceeded, Available %s in stock : %s", product.getName(), 0),
          e.getMessage());
    }
  }

  @Test
  @DisplayName("Get orders")
  void getOrderList() {
    // Initial test for empty order list
    assertEquals(0, orderService.getOrderList(user1).size());

    orderService.createOrder(user1, new OrderRequest(address1.getId()));
    // Test for non-empty order list
    assertEquals(1, orderService.getOrderList(user1).size());
  }

  private User registerAndGetUser(String userName, String userEmail, String userPassword) {
    userService.registerUser(new SignupRequest(userName, userEmail, userPassword));
    return userRepository.findByEmail(userEmail).get();
  }

  private Address getMockAddress(User user, int stateId) {
    State state = stateRepository.findById(stateId).get();
    return Address.builder()
        .user(user)
        .addressLine1("address_line_1")
        .addressLine2("address_line_2")
        .district("district")
        .state(state)
        .country(state.getCountry())
        .mobileNumber("9876543210")
        .pincode("123456")
        .type(Address.AddressType.HOME)
        .build();
  }
}
