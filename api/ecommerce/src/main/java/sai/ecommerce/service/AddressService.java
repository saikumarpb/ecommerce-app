package sai.ecommerce.service;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.Country;
import sai.ecommerce.domain.State;
import sai.ecommerce.domain.User;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.exception.NotFoundException;
import sai.ecommerce.exception.UnauthorizedException;
import sai.ecommerce.model.address.AddressRequest;
import sai.ecommerce.model.address.CountriesAndStatesResponse;
import sai.ecommerce.model.address.UpdateAddressRequest;
import sai.ecommerce.model.mapper.CountryJsonMapper;
import sai.ecommerce.model.mapper.StateJsonMapper;
import sai.ecommerce.repository.AddressRepository;
import sai.ecommerce.repository.CountryRepository;
import sai.ecommerce.repository.StateRepository;
import sai.ecommerce.utils.JsonUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {
  private static final String COUNTRIES_AND_STATES_FILE_PATH = "data/countries_and_states.json";

  private final AddressRepository addressRepository;
  private final CountryRepository countryRepository;
  private final StateRepository stateRepository;

  public List<Address> getAddressList(User user) {
    return addressRepository.findByUserId(user.getId());
  }

  public Address addAddress(User user, AddressRequest addressRequest) {
    State state =
        stateRepository
            .findById(addressRequest.getStateId())
            .orElseThrow(() -> new BadRequestException("Invalid state"));
    Address address = AddressRequest.toAddress(addressRequest, state, user);
    return addressRepository.save(address);
  }

  public Address updateAddress(User user, UpdateAddressRequest updateAddressRequest) {
    Address existingAddress = validateUserAndGetAddress(user, updateAddressRequest.getId());

    if (updateAddressRequest.getStateId() != existingAddress.getState().getId()) {
      State state =
          stateRepository
              .findById(updateAddressRequest.getStateId())
              .orElseThrow(() -> new BadRequestException("Invalid state"));
      existingAddress.setState(state);
      existingAddress.setCountry(state.getCountry());
    }

    updateExistingAddress(existingAddress, updateAddressRequest);

    return addressRepository.save(existingAddress);
  }

  private void updateExistingAddress(
      Address existingAddress, UpdateAddressRequest updateAddressRequest) {
    existingAddress.setType(updateAddressRequest.getType());
    existingAddress.setAddressLine1(updateAddressRequest.getAddressLine1());
    existingAddress.setAddressLine2(updateAddressRequest.getAddressLine2());
    existingAddress.setDistrict(updateAddressRequest.getDistrict());
    existingAddress.setPincode(updateAddressRequest.getPincode());
    existingAddress.setMobileNumber(updateAddressRequest.getMobileNumber());
  }

  public void deleteAddress(User user, int addressId) {
    Address address = validateUserAndGetAddress(user, addressId);
    addressRepository.delete(address);
  }

  public Address validateUserAndGetAddress(User user, int addressId) {
    Address address =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new NotFoundException("Address not found"));

    if (user.getId() != address.getUser().getId()) {
      throw new UnauthorizedException("User is not authorized to access this address");
    }
    return address;
  }

  public List<CountriesAndStatesResponse> getCountryAndStateList() {
    List<Country> countryList = countryRepository.findAll();
    return CountriesAndStatesResponse.fromList(countryList);
  }

  @PostConstruct
  private void loadCountriesToDatabase() {
    log.info("Reading file at : {}", COUNTRIES_AND_STATES_FILE_PATH);
    CountryJsonMapper[] countries =
        JsonUtils.json2Object(COUNTRIES_AND_STATES_FILE_PATH, CountryJsonMapper[].class);

    for (CountryJsonMapper countryJson : countries) {
      log.info("Saving country : {}", countryJson.getName());
      Country country = saveCountry(countryJson);

      for (StateJsonMapper stateJson : countryJson.getStates()) {
        log.info("Saving state : {}", stateJson.getName());
        saveState(stateJson, country);
      }
    }
  }

  private Country saveCountry(CountryJsonMapper countryJson) {
    Country country = countryRepository.findById(countryJson.getId()).orElse(new Country());

    country.setId(countryJson.getId());
    country.setName(countryJson.getName());

    countryRepository.save(country);
    return country;
  }

  private void saveState(StateJsonMapper stateJson, Country country) {
    State state = stateRepository.findById(stateJson.getId()).orElse(new State());

    state.setId(stateJson.getId());
    state.setName(stateJson.getName());
    state.setCountry(country);

    stateRepository.save(state);
  }
}
