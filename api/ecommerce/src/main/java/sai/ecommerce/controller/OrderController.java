package sai.ecommerce.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.order.OrderRequest;
import sai.ecommerce.model.order.OrderResponse;
import sai.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  public List<OrderResponse> getOrderList(@AuthenticationPrincipal User user) {
    return orderService.getOrderList(user);
  }

  @PostMapping
  public OrderResponse createOrder(
      @AuthenticationPrincipal User user, @Valid @RequestBody OrderRequest orderRequest) {
    return orderService.createOrder(user, orderRequest);
  }
}
