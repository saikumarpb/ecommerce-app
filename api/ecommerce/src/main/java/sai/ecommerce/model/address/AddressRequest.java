package sai.ecommerce.model.address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.State;
import sai.ecommerce.domain.User;

@Getter
@Setter
@AllArgsConstructor
public class AddressRequest {
  @NotNull private Address.AddressType type;

  @NotBlank private String addressLine1;

  private String addressLine2;

  @NotBlank private String district;

  @NotNull private int stateId;

  @Pattern(regexp = "^[0-9]{10}$")
  private String mobileNumber;

  @Pattern(regexp = "^[1-9][0-9]{5}$")
  private String pincode;

  public AddressRequest(AddressRequest addressRequest) {
    this.type = addressRequest.type;
    this.addressLine1 = addressRequest.addressLine1;
    this.addressLine2 = addressRequest.addressLine2;
    this.district = addressRequest.district;
    this.stateId = addressRequest.stateId;
    this.mobileNumber = addressRequest.mobileNumber;
    this.pincode = addressRequest.pincode;
  }

  public static Address toAddress(AddressRequest addressRequest, State state, User user) {
    return Address.builder()
        .type(addressRequest.getType())
        .addressLine1(addressRequest.getAddressLine1())
        .addressLine2(addressRequest.getAddressLine2())
        .district(addressRequest.getDistrict())
        .state(state)
        .country(state.getCountry())
        .user(user)
        .mobileNumber(addressRequest.getMobileNumber())
        .pincode(addressRequest.getPincode())
        .build();
  }
}
