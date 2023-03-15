package sai.ecommerce.service;

import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.ProductCategory;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.mapper.ProductCategoryJsonFileMapper;
import sai.ecommerce.model.mapper.ProductJsonMapper;
import sai.ecommerce.model.product.ProductCategoryResponse;
import sai.ecommerce.model.product.ProductDetailsResponse;
import sai.ecommerce.model.product.ProductResponse;
import sai.ecommerce.repository.ProductCategoryRepository;
import sai.ecommerce.repository.ProductRepository;
import sai.ecommerce.utils.JsonUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductRepository productRepository;

  String PRODUCT_FILE_PATH = "data/product.json";

  @Value("${load.products-json}")
  private boolean loadProducts;

  public List<ProductResponse> getProducts() {
    List<Product> products = productRepository.findAll();
    return ProductResponse.fromList(products);
  }

  public ProductDetailsResponse getProductById(int id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product not found"));

    return ProductDetailsResponse.from(product);
  }

  public List<ProductResponse> getProductsByCategory(int categoryId) {
    ProductCategory productCategory =
        productCategoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new BadRequestException("Product category not found"));

    List<Product> products = productCategory.getProducts();
    return ProductResponse.fromList(products);
  }

  public List<ProductCategoryResponse> getCategories() {
    List<ProductCategory> productCategories = productCategoryRepository.findAll();
    return ProductCategoryResponse.fromList(productCategories);
  }

  public Product getProduct(int productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new BadRequestException("Product not found"));
  }

  @PostConstruct
  private void loadProductsToDatabase() {
    if (loadProducts) {
      log.info("Reading file at : {}", PRODUCT_FILE_PATH);
      ProductCategoryJsonFileMapper[] productCategories =
          JsonUtils.json2Object(PRODUCT_FILE_PATH, ProductCategoryJsonFileMapper[].class);

      for (ProductCategoryJsonFileMapper categoryJson : productCategories) {
        log.info("Saving Product category : {}", categoryJson.getName());
        ProductCategory productCategory = saveProductCategory(categoryJson);

        for (ProductJsonMapper productJson : categoryJson.getProducts()) {
          log.info("Saving Product : {}", productJson.getName());
          saveProduct(productJson, productCategory);
        }
      }
    }
  }

  private ProductCategory saveProductCategory(ProductCategoryJsonFileMapper productCategoryJson) {
    ProductCategory category =
        productCategoryRepository
            .findById(productCategoryJson.getId())
            .orElse(new ProductCategory());

    category.setId(productCategoryJson.getId());
    category.setName(productCategoryJson.getName());
    category.setDescription(productCategoryJson.getDescription());

    productCategoryRepository.save(category);
    return category;
  }

  private void saveProduct(ProductJsonMapper productJson, ProductCategory productCategory)
      throws NoSuchElementException {
    Product product = productRepository.findById(productJson.getId()).orElse(new Product());

    product.setId(productJson.getId());
    product.setName(productJson.getName());
    product.setCategory(productCategory);
    product.setDescription(productJson.getDescription());
    product.setPrice(productJson.getPrice());
    product.setImage(productJson.getImage());
    product.setStock(productJson.getStock());

    productRepository.save(product);
  }
}
