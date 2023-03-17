package sai.ecommerce.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.model.address.CountriesAndStatesResponse;
import sai.ecommerce.service.AddressService;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CountryController {
  private final AddressService addressService;

  @GetMapping("/countries-and-states")
  public List<CountriesAndStatesResponse> getCountriesAndStatesList() {
    return addressService.getCountriesAndStatesList();
  }
}
