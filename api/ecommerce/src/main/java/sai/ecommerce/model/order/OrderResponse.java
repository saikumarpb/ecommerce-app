package sai.ecommerce.model.order;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.Order;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
  private int id;
  private double amount;
  private Address address;
  private List<OrderItemResponse> products;

  public static List<OrderResponse> fromList(List<Order> orderList) {
    return orderList.stream().map(OrderResponse::from).collect(Collectors.toList());
  }

  public static OrderResponse from(Order order) {
    List<OrderItemResponse> orderItemResponseList =
        OrderItemResponse.fromList(order.getOrderItems());

    return new OrderResponse(
        order.getId(), order.getAmount(), order.getAddress(), orderItemResponseList);
  }
}
