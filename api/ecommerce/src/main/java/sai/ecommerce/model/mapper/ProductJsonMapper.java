package sai.ecommerce.model.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductJsonMapper {
  private int id;
  private String name;
  private String description;
  private double price;
  private int stock;
  private String image;
}
