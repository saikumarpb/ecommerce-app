package sai.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sai.ecommerce.domain.Address;
import sai.ecommerce.domain.State;
import sai.ecommerce.domain.User;
import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.model.address.AddressRequest;
import sai.ecommerce.model.address.UpdateAddressRequest;
import sai.ecommerce.repository.AddressRepository;
import sai.ecommerce.repository.StateRepository;
import sai.ecommerce.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressServiceTests {
  @Autowired private AddressService addressService;
  @Autowired private AddressRepository addressRepository;
  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private StateRepository stateRepository;

  private User user1;
  private User user2;
  private Address address1;

  @BeforeEach
  public void setup() {
    String userName = "test user 1";

    String user1Email = "user1@test.com";
    String user2Email = "user2@test.com";

    String userPassword = "testPass@123";
    user1 = registerAndGetUser(userName, user1Email, userPassword);
    user2 = registerAndGetUser(userName, user2Email, userPassword);
    State state = stateRepository.findById(1).get();
    Address address = AddressRequest.toAddress(getMockAddressRequest(1, "123456"), state, user1);
    address1 = addressRepository.save(address);
  }

  private User registerAndGetUser(String userName, String userEmail, String userPassword) {
    userService.registerUser(new SignupRequest(userName, userEmail, userPassword));
    return userRepository.findByEmail(userEmail).get();
  }

  @AfterEach
  public void cleanup() {
    addressRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("Get address list")
  void getAddressList() {
    List<Address> savedAddress = addressService.getAddressList(user1);
    assertEquals(1, savedAddress.size());
  }

  @Test
  @DisplayName("Add address")
  void addAddress_success() {
    AddressRequest addressRequest = getMockAddressRequest(1, "234567");
    Address savedAddress = addressService.addAddress(user1, addressRequest);
    assertEquals("234567", savedAddress.getPincode());
  }

  @Test
  @DisplayName("Update address")
  void updateAddress_success() {
    AddressRequest addressRequest = getMockAddressRequest(1, "345678");
    UpdateAddressRequest updateAddressRequest =
        new UpdateAddressRequest(address1.getId(), addressRequest);
    addressService.updateAddress(user1, updateAddressRequest);

    Address updatedAddress = addressRepository.findById(address1.getId()).get();
    assertEquals("345678", updatedAddress.getPincode());
  }

  @Test
  @DisplayName("Update address with invalid state")
  void updateAddress_invalidState() {
    try {
      AddressRequest addressRequest = getMockAddressRequest(123456, "345678");
      UpdateAddressRequest updateAddressRequest =
          new UpdateAddressRequest(address1.getId(), addressRequest);
      addressService.updateAddress(user1, updateAddressRequest);
    } catch (Exception e) {
      assertEquals("Invalid state", e.getMessage());
    }
  }

  @Test
  @DisplayName("Update unauthorized address")
  void updateAddress_unauthorizedUser() {
    try {
      AddressRequest addressRequest = getMockAddressRequest(1, "456789");
      UpdateAddressRequest updateAddressRequest =
          new UpdateAddressRequest(address1.getId(), addressRequest);
      // user2 tries to update user1's address
      addressService.updateAddress(user2, updateAddressRequest);
    } catch (Exception e) {
      assertEquals("User is not authorized to access this address", e.getMessage());
    }
  }

  @Test
  @DisplayName("Delete address")
  void deleteAddress_success() {
    addressService.deleteAddress(user1, address1.getId());
    List<Address> addressList = addressRepository.findByUserId(address1.getId());
    assertEquals(0, addressList.size());
  }

  @Test
  @DisplayName("Delete unauthorized address")
  void deleteAddress_unauthorizedUser() {
    try {
      // user2 tries to delete user1's address
      addressService.deleteAddress(user2, address1.getId());
    } catch (Exception e) {
      assertEquals("User is not authorized to access this address", e.getMessage());
    }
  }

  private AddressRequest getMockAddressRequest(int stateId, String pincode) {
    return new AddressRequest(
        Address.AddressType.HOME,
        "address_line_1",
        "address_line_2",
        "district",
        stateId,
        "9876543210",
        pincode);
  }
}
