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
    return mapProductsToProductResponseList(products);
  }

  public ProductDetailsResponse getProductById(int id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product not found"));

    return mapProductToProductDetails(product);
  }

  public List<ProductResponse> getProductsByCategory(int categoryId) {
    ProductCategory productCategory =
        productCategoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new BadRequestException("Product category not found"));

    List<Product> products = productCategory.getProducts();
    return mapProductsToProductResponseList(products);
  }

  public List<ProductCategoryResponse> getCategories() {
    List<ProductCategory> productCategories = productCategoryRepository.findAll();
    return mapProductCategoriesToResponse(productCategories);
  }

  public Product getProduct(int productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new BadRequestException("Product not found"));
  }

  private List<ProductResponse> mapProductsToProductResponseList(List<Product> products) {
    List<ProductResponse> productsResposeList = new ArrayList<>();

    for (Product product : products) {
      ProductResponse productResponse = mapProductToProductResponse(product);
      productsResposeList.add(productResponse);
    }

    return productsResposeList;
  }

  private List<ProductCategoryResponse> mapProductCategoriesToResponse(
      List<ProductCategory> productCategories) {
    List<ProductCategoryResponse> productCategoryList = new ArrayList<>();

    for (ProductCategory productCategory : productCategories) {
      ProductCategoryResponse productCategoryResponse =
          new ProductCategoryResponse(
              productCategory.getId(), productCategory.getName(), productCategory.getDescription());
      productCategoryList.add(productCategoryResponse);
    }

    return productCategoryList;
  }

  private ProductDetailsResponse mapProductToProductDetails(Product product) {
    return new ProductDetailsResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getImage(),
        product.getCategory().getId(),
        product.getDescription(),
        product.getStock());
  }

  public ProductResponse mapProductToProductResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getImage(),
        product.getCategory().getId());
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
