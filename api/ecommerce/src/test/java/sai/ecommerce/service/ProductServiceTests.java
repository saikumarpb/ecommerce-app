package sai.ecommerce.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.product.ProductCategoryResponse;
import sai.ecommerce.model.product.ProductDetailsResponse;
import sai.ecommerce.model.product.ProductResponse;

@SpringBootTest
@Transactional
public class ProductServiceTests {
  @Autowired private ProductService productService;

  @Test
  @DisplayName("getProducts")
  void testGetProducts() {
    List<ProductResponse> productResponse = productService.getProducts();
    Assertions.assertTrue(productResponse.size() > 0);
  }

  @Test
  @DisplayName("getProductById")
  void testGetProductById() {
    ProductDetailsResponse productDetailsResponse = productService.getProductById(1);
    Assertions.assertNotNull(productDetailsResponse);
    Assertions.assertEquals(1, productDetailsResponse.getId());
  }

  @Test
  @DisplayName("getProductById failure test")
  void testGetProductByIdFailure() {
    try {
      productService.getProductById(123);
    } catch (BadRequestException e) {
      Assertions.assertEquals("Product not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("getProductsByCategory")
  void testGetProductsByCategory() {
    List<ProductResponse> productList = productService.getProductsByCategory(1);
    Assertions.assertTrue(productList.size() > 0);
    Assertions.assertEquals(1, productList.get(0).getCategoryId());
  }

  @Test
  @DisplayName("getProductsByCategory failure test")
  void testGetProductsByCategoryFailure() {
    try {
      productService.getProductsByCategory(123);
    } catch (BadRequestException e) {
      Assertions.assertEquals("Product category not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("getCategories")
  void testGetCategories() {
    List<ProductCategoryResponse> productCategoriesResponse = productService.getCategories();
    Assertions.assertTrue(productCategoriesResponse.size() > 0);
  }
}
