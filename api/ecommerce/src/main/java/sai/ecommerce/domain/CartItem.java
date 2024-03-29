package sai.ecommerce.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
public class CartItem extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  @NotNull
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @NotNull
  private Product product;

  @NotNull @PositiveOrZero private int quantity;

  public CartItem(Cart cart, Product product, int quantity) {
    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
  }
}
