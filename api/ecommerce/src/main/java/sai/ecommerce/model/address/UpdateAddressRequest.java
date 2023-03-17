package sai.ecommerce.model.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressRequest extends AddressRequest {
  private int id;

  public UpdateAddressRequest(int id, AddressRequest addressRequest) {
    super(addressRequest);
    this.id = id;
  }
}
