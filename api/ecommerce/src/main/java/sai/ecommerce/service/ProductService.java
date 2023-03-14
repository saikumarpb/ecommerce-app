package sai.ecommerce.service;

import java.util.ArrayList;
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

  String PRODUCT_FILE_PATH = "data/product.json";

  @Value("${load.products-json}")
  private boolean loadProducts;

  public List<ProductJsonMapper> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return mapProductsToProductJson(products);
  }

  public ProductJsonMapper getProductById(int id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product not found"));

    return mapProductToProductJson(product);
  }

  public List<ProductJsonMapper> getProductsByCategory(int id) {
    ProductCategory productCategory =
        productCategoryRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product category not found"));

    List<Product> products = productCategory.getProducts();
    return mapProductsToProductJson(products);
  }

  public List<ProductCategoryJsonFileMapper> getAllProductsByCategory() {
    List<ProductCategory> productCategories = productCategoryRepository.findAll();
    return mapProductCategoriesToJsonFile(productCategories);
  }

  public List<ProductCategoryJsonMapper> getAllCategories() {
    List<ProductCategory> productCategories = productCategoryRepository.findAll();
    return mapProductCategoriesToJson(productCategories);
  }

  private List<ProductJsonMapper> mapProductsToProductJson(List<Product> products) {
    List<ProductJsonMapper> productsJson = new ArrayList<>();

    for (Product product : products) {
      ProductJsonMapper productJson = mapProductToProductJson(product);
      productsJson.add(productJson);
    }

    return productsJson;
  }

  private List<ProductCategoryJsonMapper> mapProductCategoriesToJson(
      List<ProductCategory> productCategories) {
    List<ProductCategoryJsonMapper> productCategoryJsonList = new ArrayList<>();

    for (ProductCategory productCategory : productCategories) {
      ProductCategoryJsonMapper productCategoryJson =
          new ProductCategoryJsonMapper(
              productCategory.getId(), productCategory.getName(), productCategory.getDescription());
      productCategoryJsonList.add(productCategoryJson);
    }

    return productCategoryJsonList;
  }

  private List<ProductCategoryJsonFileMapper> mapProductCategoriesToJsonFile(
      List<ProductCategory> productCategories) {
    List<ProductCategoryJsonFileMapper> productCategoryJsonList = new ArrayList<>();

    for (ProductCategory productCategory : productCategories) {
      List<Product> products = productCategory.getProducts();

      ProductCategoryJsonFileMapper productCategoryJson =
          new ProductCategoryJsonFileMapper(
              productCategory.getId(),
              productCategory.getName(),
              productCategory.getDescription(),
              mapProductsToProductJson(products));

      productCategoryJsonList.add(productCategoryJson);
    }

    return productCategoryJsonList;
  }

  public ProductJsonMapper mapProductToProductJson(Product product) {
    return new ProductJsonMapper(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStock(),
        product.getImage());
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
