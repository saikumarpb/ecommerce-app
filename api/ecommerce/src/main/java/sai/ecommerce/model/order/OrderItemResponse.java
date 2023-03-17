package sai.ecommerce.model.order;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sai.ecommerce.domain.OrderItem;
import sai.ecommerce.domain.Product;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponse {
  private int productId;
  private String productName;
  private double price;
  private int quantity;

  public static List<OrderItemResponse> fromList(List<OrderItem> orderItemList) {
    return orderItemList.stream().map(OrderItemResponse::from).collect(Collectors.toList());
  }

  public static OrderItemResponse from(OrderItem orderItem) {
    Product product = orderItem.getProduct();

    return new OrderItemResponse(
        product.getId(), product.getName(), orderItem.getPrice(), orderItem.getQuantity());
  }
}
