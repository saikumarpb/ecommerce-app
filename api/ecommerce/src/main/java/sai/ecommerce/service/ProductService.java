package sai.ecommerce.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  String PRODUCT_CATEGORY_DATA_PATH = "data/product_category.json";
  String PRODUCT_DATA_PATH = "data/product.json";

  @PostConstruct
  private void loadProductsToDatabase() {
    log.info(String.format("Reading file at : %s", PRODUCT_CATEGORY_DATA_PATH));
    ProductCategoryJsonMapper[] productCategories =
        JsonUtils.json2Object(PRODUCT_CATEGORY_DATA_PATH, ProductCategoryJsonMapper[].class);

    for (ProductCategoryJsonMapper category : productCategories) {
      log.info(String.format("Saving Product category : %s", category.getName()));
      saveProductCategory(category);
    }

    log.info(String.format("Reading file at : %s", PRODUCT_DATA_PATH));
    ProductJsonMapper[] products =
        JsonUtils.json2Object(PRODUCT_DATA_PATH, ProductJsonMapper[].class);
    for (ProductJsonMapper product : products) {
      log.info(String.format("Saving Product : %s", product.getName()));
      saveProduct(product);
    }
  }

  private void saveProductCategory(ProductCategoryJsonMapper saveCategory) {
    ProductCategory category =
        productCategoryRepository.findById(saveCategory.getId()).orElse(new ProductCategory());
    category.setId(saveCategory.getId());
    category.setName(saveCategory.getName());
    category.setDescription(saveCategory.getDescription());
    productCategoryRepository.save(category);
  }

  private void saveProduct(ProductJsonMapper saveProduct) throws NoSuchElementException {
    Product product = productRepository.findById(saveProduct.getId()).orElse(new Product());
    Optional<ProductCategory> productcategory =
        productCategoryRepository.findById(saveProduct.getCategoryId());
    if (!productcategory.isPresent()) {
      throw new NoSuchElementException("Category not found");
    }
    product.setId(saveProduct.getId());
    product.setName(saveProduct.getName());
    product.setCategory(productcategory.get());
    product.setDescription(saveProduct.getDescription());
    product.setPrice(saveProduct.getPrice());
    product.setImage(saveProduct.getImage());
    product.setStock(saveProduct.getStock());
    productRepository.save(product);
  }
}
