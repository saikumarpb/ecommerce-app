package sai.ecommerce.model.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sai.ecommerce.domain.ProductCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryResponse {
  private int id;
  private String name;
  private String description;

  public static List<ProductCategoryResponse> fromList(List<ProductCategory> productCategories) {
    return productCategories.stream()
        .map(pc -> new ProductCategoryResponse(pc.getId(), pc.getName(), pc.getDescription()))
        .collect(Collectors.toList());
  }
}
