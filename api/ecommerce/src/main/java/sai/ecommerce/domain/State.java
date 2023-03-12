package sai.ecommerce.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "state")
@Getter
@Setter
public class State extends BaseEntity {
  @Id private int id;

  @NotBlank private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  @NotNull
  private Country country;
}
