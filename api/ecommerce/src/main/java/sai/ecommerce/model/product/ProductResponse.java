package sai.ecommerce.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
