package ee.meat.shop.controller;

import ee.meat.shop.dto.CartItemDTO;
import ee.meat.shop.dto.CartItemRequest;
import ee.meat.shop.model.CartItem;
import ee.meat.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        CartItem cartItem = cartService.addCartItem(cartItemRequest.getUserId(), cartItemRequest.getProductId(), cartItemRequest.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getAllCartItemsForUser(@PathVariable Long userId) {
        List<CartItemDTO> cartItems = cartService.listCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
    @PostMapping("/cart/placeOrder/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long userId) {
        try {
            cartService.placeOrder(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order: " + e.getMessage());
        }
    }


}
