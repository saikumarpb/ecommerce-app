package sai.ecommerce.model.address;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sai.ecommerce.domain.Country;
import sai.ecommerce.domain.State;

@Getter
@Setter
@AllArgsConstructor
public class CountriesAndStatesResponse {
  private int id;
  private String name;
  private List<State> states;

  public static List<CountriesAndStatesResponse> fromList(List<Country> countryList) {
    return countryList.stream().map(CountriesAndStatesResponse::from).collect(Collectors.toList());
  }

  public static CountriesAndStatesResponse from(Country country) {
    return new CountriesAndStatesResponse(country.getId(), country.getName(), country.getStates());
  }
}
