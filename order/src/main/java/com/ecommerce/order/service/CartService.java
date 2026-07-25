package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequestDto;
import com.ecommerce.order.model.CartItem;

import java.util.List;

public interface CartService {
    boolean addToCart(String userId, CartItemRequestDto cartItemRequestDto);

    boolean deleteItemFromCart(String userId, Long productId);

    List<CartItem> getCart(String userId);

    void clearCart(String userId);
}
