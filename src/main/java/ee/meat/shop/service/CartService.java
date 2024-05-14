package ee.meat.shop.service;

import ee.meat.shop.dto.CartItemDTO;
import ee.meat.shop.model.CartItem;
import ee.meat.shop.model.Product;
import ee.meat.shop.model.User;
import ee.meat.shop.repository.CartItemRepository;
import ee.meat.shop.repository.ProductRepository;
import ee.meat.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartItem addCartItem(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity); // Increment existing quantity
                    return item;
                })
                .orElse(new CartItem(userId, productId, quantity)); // Or create new with given quantity
        return cartItemRepository.save(cartItem);
    }


    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    public List<CartItemDTO> listCartItems(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUser_Id(userId);
        return cartItems.stream()
                .map(item -> new CartItemDTO(
                        item.getId(),
                        item.getUser().getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
    }
    public void placeOrder(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
            cartItemRepository.delete(item);
        }
    }



}
