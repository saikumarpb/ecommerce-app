package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
  Optional<ProductCategory> findByName(String name);
}
