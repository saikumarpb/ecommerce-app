package sai.ecommerce.utils;

import sai.ecommerce.model.SignupRequest;
import sai.ecommerce.model.cart.UpdateCartRequest;

public class MockData {
  public static String userName = "test user";
  public static String userEmail = "user@test.com";
  public static String userPassword = "testPass@123";

  public static SignupRequest getSignupRequest(String email) {
    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setName(userName);
    signupRequest.setEmail(email);
    signupRequest.setPassword(userPassword);
    return signupRequest;
  }

  public static UpdateCartRequest getUpdateCartRequest(int productId, int quantity) {
    UpdateCartRequest updateCartRequest = new UpdateCartRequest();
    updateCartRequest.setProductId(productId);
    updateCartRequest.setQuantity(quantity);
    return updateCartRequest;
  }
}
