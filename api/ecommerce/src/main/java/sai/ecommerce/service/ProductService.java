package sai.ecommerce.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.ProductCategory;
import sai.ecommerce.model.mapper.ProductCategoryJsonMapper;
import sai.ecommerce.model.mapper.ProductJsonMapper;
import sai.ecommerce.repository.ProductCategoryRepository;
import sai.ecommerce.repository.ProductRepository;
import sai.ecommerce.utils.JsonUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductRepository productRepository;

  String PRODUCT_CATEGORY_FILE_PATH = "data/product_category.json";
  String PRODUCT_FILE_PATH = "data/product.json";

  @Value("${load.products-json}")
  private boolean loadProducts;

  @PostConstruct
  private void loadProductsToDatabase() {
    log.info("Reading file at : {}", PRODUCT_CATEGORY_FILE_PATH);
    ProductCategoryJsonMapper[] productCategories =
        JsonUtils.json2Object(PRODUCT_CATEGORY_FILE_PATH, ProductCategoryJsonMapper[].class);

    for (ProductCategoryJsonMapper category : productCategories) {
      log.info("Saving Product category : {}", category.getName());
      saveProductCategory(category);
    }

    log.info("Reading file at : {}", PRODUCT_FILE_PATH);
    ProductJsonMapper[] products =
        JsonUtils.json2Object(PRODUCT_FILE_PATH, ProductJsonMapper[].class);

    for (ProductJsonMapper product : products) {
      log.info("Saving Product : {}", product.getName());
      saveProduct(product);
    }
  }

  private void saveProductCategory(ProductCategoryJsonMapper productCategoryJson) {
    ProductCategory category =
        productCategoryRepository
            .findById(productCategoryJson.getId())
            .orElse(new ProductCategory());

    category.setId(productCategoryJson.getId());
    category.setName(productCategoryJson.getName());
    category.setDescription(productCategoryJson.getDescription());

    productCategoryRepository.save(category);
  }

  private void saveProduct(ProductJsonMapper productJson) throws NoSuchElementException {
    Product product = productRepository.findById(productJson.getId()).orElse(new Product());

    Optional<ProductCategory> productcategory =
        productCategoryRepository.findById(productJson.getCategoryId());
    if (!productcategory.isPresent()) {
      throw new NoSuchElementException("Product category not found");
    }

    product.setId(productJson.getId());
    product.setName(productJson.getName());
    product.setCategory(productcategory.get());
    product.setDescription(productJson.getDescription());
    product.setPrice(productJson.getPrice());
    product.setImage(productJson.getImage());
    product.setStock(productJson.getStock());
    
    productRepository.save(product);
  }
}
