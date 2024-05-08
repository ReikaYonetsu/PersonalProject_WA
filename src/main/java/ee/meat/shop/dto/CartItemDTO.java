package ee.meat.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemDTO {
    // Getters and Setters
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;

    // Constructor to convert entity to DTO
    public CartItemDTO(Long id, Long userId, Long productId, String productName, double productPrice, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

}
