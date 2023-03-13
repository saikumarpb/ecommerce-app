package sai.ecommerce.model.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryJsonMapper {
  private int id;
  private String name;
  private String description;
  private ProductJsonMapper[] products;
}
