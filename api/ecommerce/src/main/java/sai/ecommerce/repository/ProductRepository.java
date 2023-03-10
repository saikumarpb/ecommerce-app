package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  Optional<Product> findByName(String name);
}
