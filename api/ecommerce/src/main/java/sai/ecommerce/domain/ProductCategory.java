package sai.ecommerce.domain;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(
    name = "product_category",
    uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@RequiredArgsConstructor
public class ProductCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private int id;

  @NotBlank private String name;

  private String description;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Product> products;
}
