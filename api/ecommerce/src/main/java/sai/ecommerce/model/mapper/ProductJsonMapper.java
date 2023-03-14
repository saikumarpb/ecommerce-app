package sai.ecommerce.model.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductJsonMapper {
  private int id;
  private String name;
  private String description;
  private double price;
  private int stock;
  private String image;
}
