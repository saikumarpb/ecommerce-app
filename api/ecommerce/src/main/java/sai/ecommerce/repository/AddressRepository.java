package sai.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
  List<Address> findByUserId(int userId);
}
