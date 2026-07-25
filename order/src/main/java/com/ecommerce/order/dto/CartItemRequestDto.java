package com.ecommerce.order.dto;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long productId;
    private Integer quantity;
}
