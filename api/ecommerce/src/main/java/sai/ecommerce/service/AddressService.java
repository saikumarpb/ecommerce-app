package sai.ecommerce.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Country;
import sai.ecommerce.domain.State;
import sai.ecommerce.model.mapper.CountryJsonMapper;
import sai.ecommerce.model.mapper.StateJsonMapper;
import sai.ecommerce.repository.CountryRepository;
import sai.ecommerce.repository.StateRepository;
import sai.ecommerce.utils.JsonUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {
  String COUNTRY_DATA_PATH = "data/country.json";
  String STATE_DATA_PATH = "data/state.json";

  private final CountryRepository countryRepository;
  private final StateRepository stateRepository;

  @Value("${load.countries-json}")
  private boolean loadCountries;

  @PostConstruct
  private void loadCountriesToDatabase() {
    if (loadCountries) {
      log.info(String.format("Reading file at : %s", COUNTRY_DATA_PATH));
      CountryJsonMapper[] countries =
          JsonUtils.json2Object(COUNTRY_DATA_PATH, CountryJsonMapper[].class);

      for (CountryJsonMapper country : countries) {
        log.info(String.format("Saving country : %s", country.getName()));
        saveCountry(country);
      }

      log.info(String.format("Reading file at : %s", STATE_DATA_PATH));
      StateJsonMapper[] states = JsonUtils.json2Object(STATE_DATA_PATH, StateJsonMapper[].class);

      for (StateJsonMapper state : states) {
        log.info(String.format("Saving state : %s", state.getName()));
        saveState(state);
      }
    }
  }

  private void saveCountry(CountryJsonMapper saveCountry) {
    Country country = countryRepository.findById(saveCountry.getId()).orElse(new Country());
    country.setId(saveCountry.getId());
    country.setName(saveCountry.getName());
    countryRepository.save(country);
  }

  private void saveState(StateJsonMapper saveState) {
    State state = stateRepository.findById(saveState.getId()).orElse(new State());
    Optional<Country> country = countryRepository.findById(saveState.getCountryId());
    if (!country.isPresent()) {
      throw new NoSuchElementException("Country not found");
    }
    state.setId(saveState.getId());
    state.setName(saveState.getName());
    state.setCountry(country.get());
    stateRepository.save(state);
  }
}
