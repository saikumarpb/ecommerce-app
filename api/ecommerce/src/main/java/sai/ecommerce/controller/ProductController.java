package sai.ecommerce.controller;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.product.ProductCategoryResponse;
import sai.ecommerce.model.product.ProductDetailsResponse;
import sai.ecommerce.model.product.ProductResponse;
import sai.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductController {
  private final ProductService productService;

  @GetMapping("/{productId}")
  public ProductDetailsResponse getProductById(@PathVariable int productId)
      throws BadRequestException {
    return productService.getProductById(productId);
  }

  @GetMapping
  public List<ProductResponse> getProducts(@RequestParam Optional<Integer> categoryId) {
    if (categoryId.isPresent()) {
      return productService.getProductsByCategory(categoryId.get());
    }
    return productService.getProducts();
  }

  @GetMapping("/categories")
  public List<ProductCategoryResponse> getCategories() {
    return productService.getCategories();
  }
}
