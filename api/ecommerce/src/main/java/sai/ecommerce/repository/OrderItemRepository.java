package sai.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {}
