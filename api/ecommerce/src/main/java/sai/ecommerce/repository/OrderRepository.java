package sai.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findByUserId(int userId);
}
