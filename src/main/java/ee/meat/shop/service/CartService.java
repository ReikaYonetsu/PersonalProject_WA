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
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

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


}
