package sai.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private AddressType type;

  @Column(name = "address_line_1")
  @NotBlank
  private String addressLine1;

  @Column(name = "address_line_2")
  private String addressLine2;

  @NotBlank private String district;

  @ManyToOne
  @JoinColumn(name = "state_id")
  @NotNull
  private State state;

  @ManyToOne
  @JoinColumn(name = "country_id")
  @NotNull
  private Country country;

  @NotBlank private String pincode;

  @NotBlank
  @Column(name = "mobile_number")
  private String mobileNumber;

  public enum AddressType {
    WORK,
    HOME
  }
}
