package sai.ecommerce.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.address.AddressRequest;
import sai.ecommerce.model.address.UpdateAddressRequest;
import sai.ecommerce.service.AddressService;

@RestController
@RequestMapping("/addresses")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AddressController {

  private final AddressService addressService;

  @GetMapping
  public List<Address> getAddressList(@AuthenticationPrincipal User user) {
    return addressService.getAddressList(user);
  }

  @PostMapping
  public Address addAddress(
      @AuthenticationPrincipal User user, @Valid @RequestBody AddressRequest addressRequest) {
    return addressService.addAddress(user, addressRequest);
  }

  @PutMapping
  public Address updateAddress(
      @AuthenticationPrincipal User user,
      @Valid @RequestBody UpdateAddressRequest updateAddressRequest) {
    return addressService.updateAddress(user, updateAddressRequest);
  }

  @DeleteMapping("/{addressId}")
  public void deleteAddress(@AuthenticationPrincipal User user, @PathVariable int addressId) {
    addressService.deleteAddress(user, addressId);
  }
}
