package sai.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sai.ecommerce.domain.Cart;
import sai.ecommerce.domain.CartItem;
import sai.ecommerce.domain.Product;
import sai.ecommerce.domain.User;
import sai.ecommerce.exception.BadRequestException;
import sai.ecommerce.model.cart.CartItemResponse;
import sai.ecommerce.model.cart.UpdateCartRequest;
import sai.ecommerce.model.product.ProductResponse;
import sai.ecommerce.repository.CartItemRepository;
import sai.ecommerce.repository.CartRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductService productService;

  public List<CartItemResponse> getUserCart(int userId) {
    Cart userCart = cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));
    return mapCartItemsToResponseList(userCart.getCartItems());
  }

  public List<CartItemResponse> updateUserCart(int userId, UpdateCartRequest updateCartRequest) {
    int quantity = updateCartRequest.getQuantity();

    Product product = productService.getProduct(updateCartRequest.getProductId());
    validateQuantity(product, quantity);

    Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));
    Optional<CartItem> cartItem =
        cartItemRepository.findByCartIdAndProductId(cart.getId(), updateCartRequest.getProductId());

    if (cartItem.isPresent()) {
      if (quantity == 0) {
        deleteCartItem(cartItem.get());
      } else {
        cartItem.get().setQuantity(quantity);
        cartItemRepository.save(cartItem.get());
      }
    } else {
      if (quantity == 0) {
        throw new BadRequestException("Invalid operation");
      } else {
        CartItem newCartItem = createCartItem(cart, product, quantity);
        cartItemRepository.save(newCartItem);
      }
    }

    List<CartItem> cartItems = cartRepository.findByUserId(userId).get().getCartItems();
    return mapCartItemsToResponseList(cartItems);
  }

  public CartItem createCartItem(Cart cart, Product product, int quantity) {
    CartItem newCartItem = new CartItem();
    newCartItem.setCart(cart);
    newCartItem.setProduct(product);
    newCartItem.setQuantity(quantity);
    return newCartItem;
  }

  public void deleteCartItem(CartItem cartItem) {
    cartItemRepository.deleteById(cartItem.getId());
  }

  public void validateQuantity(Product product, int quantity) {
    int availableQuantity = product.getStock();
    if (quantity > availableQuantity) {
      throw new BadRequestException(
          String.format("Quantity exceeded, Available items in stock : %s", availableQuantity));
    } else if (quantity < 0) {
      throw new BadRequestException("Invalid quantity");
    }
  }

  public Cart createNewCart(int userId) {
    Cart newCart = new Cart();
    User user = new User();
    user.setId(userId);
    cartRepository.save(newCart);
    return cartRepository.findByUserId(userId).get();
  }

  public List<CartItemResponse> mapCartItemsToResponseList(List<CartItem> cartItemList) {
    List<CartItemResponse> cartItemResponseList = new ArrayList<>();
    for (CartItem cartItem : cartItemList) {
      cartItemResponseList.add(mapCartItemToResponse(cartItem));
    }
    return cartItemResponseList;
  }

  public CartItemResponse mapCartItemToResponse(CartItem cartItem) {
    CartItemResponse cartItemResponse = new CartItemResponse();
    ProductResponse product = productService.mapProductToProductResponse(cartItem.getProduct());
    cartItemResponse.setProductId(product.getId());
    cartItemResponse.setProductName(product.getName());
    cartItemResponse.setPrice(product.getPrice());
    cartItemResponse.setQuantity(cartItem.getQuantity());
    return cartItemResponse;
  }
}
