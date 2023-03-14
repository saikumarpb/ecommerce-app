package sai.ecommerce.model.mapper;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryJsonFileMapper extends ProductCategoryJsonMapper {
  private List<ProductJsonMapper> products;

  @Builder
  public ProductCategoryJsonFileMapper(
      int id, String name, String description, List<ProductJsonMapper> products) {
    super(id, name, description);
    this.products = products;
  }
}
