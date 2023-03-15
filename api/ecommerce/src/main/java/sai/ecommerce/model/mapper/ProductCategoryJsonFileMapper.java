package sai.ecommerce.model.mapper;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sai.ecommerce.model.product.ProductCategoryResponse;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryJsonFileMapper extends ProductCategoryResponse {
  private List<ProductJsonMapper> products;
}
