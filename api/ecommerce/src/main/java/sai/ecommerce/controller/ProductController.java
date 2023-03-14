package sai.ecommerce.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.model.ApiResponse;
import sai.ecommerce.model.mapper.ProductCategoryJsonFileMapper;
import sai.ecommerce.model.mapper.ProductCategoryJsonMapper;
import sai.ecommerce.model.mapper.ProductJsonMapper;
import sai.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductController {
  private final ProductService productService;

  String PRODUCT_FETCH_SUCCESS = "Product fetch successful.";
  String PRODUCT_CATEGORY_FETCH_SUCCESS = "Product category fetch successful.";

  @GetMapping("/products/id/{id}")
  public ApiResponse<ProductJsonMapper> getProductById(@PathVariable int id) {
    ProductJsonMapper productJson = productService.getProductById(id);
    return new ApiResponse(PRODUCT_FETCH_SUCCESS, productJson);
  }

  @GetMapping("/products/all")
  public ApiResponse<List<ProductJsonMapper>> getAllProducts() {
    List<ProductJsonMapper> productJson = productService.getAllProducts();
    return new ApiResponse(PRODUCT_FETCH_SUCCESS, productJson);
  }

  @GetMapping("/products/category/{categoryId}")
  public ApiResponse<List<ProductJsonMapper>> getProductsByCategory(@PathVariable int categoryId) {
    List<ProductJsonMapper> productsJson = productService.getProductsByCategory(categoryId);
    return new ApiResponse(PRODUCT_CATEGORY_FETCH_SUCCESS, productsJson);
  }

  @GetMapping("/products/category/all")
  public ApiResponse<List<ProductCategoryJsonFileMapper>> getAllProductsByCategory() {
    List<ProductCategoryJsonFileMapper> productCategories =
        productService.getAllProductsByCategory();
    return new ApiResponse(PRODUCT_FETCH_SUCCESS, productCategories);
  }

  @GetMapping("/product-category/all")
  public ApiResponse<List<ProductCategoryJsonMapper>> getAllCategories() {
    List<ProductCategoryJsonMapper> productCategories = productService.getAllCategories();
    return new ApiResponse(PRODUCT_CATEGORY_FETCH_SUCCESS, productCategories);
  }
}
