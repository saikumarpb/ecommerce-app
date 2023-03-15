package sai.ecommerce.model.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sai.ecommerce.domain.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
  private int id;
  private String name;
  private double price;
  private String image;
  private int categoryId;

  public static List<ProductResponse> fromList(List<Product> products) {
    return products.stream().map(ProductResponse::from).collect(Collectors.toList());
  }

  public static ProductResponse from(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getImage(),
        product.getCategory().getId());
  }
}
