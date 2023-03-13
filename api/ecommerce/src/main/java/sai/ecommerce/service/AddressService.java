package sai.ecommerce.service;

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
  String COUNTRY_FILE_PATH = "data/country.json";

  private final CountryRepository countryRepository;
  private final StateRepository stateRepository;

  @Value("${load.countries-json}")
  private boolean loadCountries;

  @PostConstruct
  private void loadCountriesToDatabase() {
    if (loadCountries) {
      log.info("Reading file at : {}", COUNTRY_FILE_PATH);
      CountryJsonMapper[] countries =
          JsonUtils.json2Object(COUNTRY_FILE_PATH, CountryJsonMapper[].class);

      for (CountryJsonMapper countryJson : countries) {
        log.info("Saving country : {}", countryJson.getName());
        Country country = saveCountry(countryJson);

        for (StateJsonMapper stateJson : countryJson.getStates()) {
          log.info("Saving state : %s", stateJson.getName());
          saveState(stateJson, country);
        }
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
