package ee.meat.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequest {
    private Long userId;
    private Long productId;
    private int quantity;


}