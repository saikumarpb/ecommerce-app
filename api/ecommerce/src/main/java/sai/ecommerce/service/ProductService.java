package sai.ecommerce.service;

import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.ProductCategory;
import sai.ecommerce.model.ProductCategoryJson;
import sai.ecommerce.model.ProductJson;
import sai.ecommerce.repository.ProductCategoryRepository;
import sai.ecommerce.repository.ProductRepository;
import sai.ecommerce.utils.JsonUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductRepository productRepository;

  private final String PRODUCT_CATEGORY_DATA_PATH = "data/product_category.json";
  private final String PRODUCT_DATA_PATH = "data/product.json";

  @PostConstruct
  private void loadProductsToDatabase() throws IOException {
    log.info(String.format("Reading file at : %s", PRODUCT_CATEGORY_DATA_PATH));
    ProductCategoryJson[] productCategories =
        JsonUtils.json2Object(PRODUCT_CATEGORY_DATA_PATH, ProductCategoryJson[].class);

    for (int i = 0; i < productCategories.length; i++) {
      log.info(String.format("Saving Product category : %s", productCategories[i].getName()));
      saveProductCategory(productCategories[i]);
    }

    log.info(String.format("Reading file at : %s", PRODUCT_DATA_PATH));
    ProductJson[] products = JsonUtils.json2Object(PRODUCT_DATA_PATH, ProductJson[].class);
    for (int i = 0; i < products.length; i++) {
      log.info(String.format("Saving Product : %s", products[i].getName()));
      saveProduct(products[i]);
    }
  }

  private void saveProductCategory(ProductCategoryJson saveCategory) {
    ProductCategory category =
        productCategoryRepository.findByName(saveCategory.getName()).orElse(new ProductCategory());
    category.setName(saveCategory.getName());
    category.setDescription(saveCategory.getDescription());
    productCategoryRepository.save(category);
  }

  private void saveProduct(ProductJson saveProduct) {
    Product product = productRepository.findByName(saveProduct.getName()).orElse(new Product());
    ProductCategory productcategory =
        productCategoryRepository.findById(saveProduct.getCategoryId()).get();
    product.setName(saveProduct.getName());
    product.setCategory(productcategory);
    product.setDescription(saveProduct.getDescription());
    product.setPrice(saveProduct.getPrice());
    product.setImage(saveProduct.getImage());
    product.setStock(saveProduct.getStock());
    productRepository.save(product);
  }
}
