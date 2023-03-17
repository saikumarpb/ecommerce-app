package sai.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.Cart;
import sai.ecommerce.domain.CartItem;
import sai.ecommerce.domain.Order;
import sai.ecommerce.domain.OrderItem;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.User;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.order.OrderResponse;
import sai.ecommerce.repository.CartRepository;
import sai.ecommerce.repository.OrderItemRepository;
import sai.ecommerce.repository.OrderRepository;
import sai.ecommerce.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final AddressService addressService;
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final CartService cartService;

  private static final String EMPTY_CART_ERROR = "Order cannot be placed with empty cart";

  public List<OrderResponse> getOrderList(User user) {
    List<Order> orderList = orderRepository.findByUserId(user.getId());
    return OrderResponse.fromList(orderList);
  }

  public OrderResponse placeOrder(User user, int addressId) {
    Address address = addressService.validateUserAndGetAddress(user, addressId);

    Cart cart =
        cartRepository
            .findByUserId(user.getId())
            .orElseThrow(() -> new BadRequestException(EMPTY_CART_ERROR));

    List<CartItem> cartItemList = cart.getCartItems();
    if (cartItemList.isEmpty()) {
      throw new BadRequestException(EMPTY_CART_ERROR);
    }

    double amount = cartItemList.stream().mapToDouble(this::calculateItemTotal).sum();
    Order order =
        orderRepository.save(Order.builder().user(user).amount(amount).address(address).build());

    List<OrderItem> orderItemList = new ArrayList<>();

    for (CartItem cartItem : cartItemList) {
      Product product = cartItem.getProduct();
      int quantity = cartItem.getQuantity();
      OrderItem orderItem = createOrderItem(order, product, quantity);
      orderItemList.add(orderItemRepository.save(orderItem));
      product.setStock(product.getStock() - quantity);
      productRepository.save(product);
    }
    cartRepository.deleteById(cart.getId());
    order.setOrderItems(orderItemList);
    return OrderResponse.from(order);
  }

  private OrderItem createOrderItem(Order order, Product product, int quantity) {
    return OrderItem.builder()
        .order(order)
        .product(product)
        .price(product.getPrice())
        .quantity(quantity)
        .build();
  }

  private double calculateItemTotal(CartItem cartItem) {
    Product product = cartItem.getProduct();
    int quantity = cartItem.getQuantity();
    cartService.validateQuantity(product, quantity);
    return product.getPrice() * quantity;
  }
}
