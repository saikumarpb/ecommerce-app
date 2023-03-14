package sai.ecommerce.utils;

import org.springframework.stereotype.Component;

@Component
public class TypeChecker {
  public static boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
