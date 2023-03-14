package sai.ecommerce.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailsResponse extends ProductResponse {
  private String description;
  private int stock;

  @Builder
  public ProductDetailsResponse(
      int id,
      String name,
      double price,
      String image,
      int categoryId,
      String description,
      int stock) {
    super(id, name, price, image, categoryId);
    this.description = description;
    this.stock = stock;
  }
}
