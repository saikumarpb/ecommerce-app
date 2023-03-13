package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
  Optional<Country> findById(int id);
}
