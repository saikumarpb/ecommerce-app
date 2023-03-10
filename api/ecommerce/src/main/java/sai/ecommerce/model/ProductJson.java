package sai.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductJson {
  private String name;
  private String description;
  private int categoryId;
  private double price;
  private int stock;
  private String image;
}
