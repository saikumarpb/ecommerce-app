package sai.ecommerce.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @NotBlank private AddressType type;

  @Column(name = "address_line_1")
  @NotBlank
  private String addressLine1;

  @Column(name = "address_line_2")
  private String addressLine2;

  @NotBlank private String district;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "state_id")
  @NotBlank
  private State state;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  @NotBlank
  private Country country;

  @NotBlank private String pincode;

  @NotBlank
  @Column(name = "mobile_number")
  private String mobileNumber;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private enum AddressType {
    WORK,
    HOME
  }
}
