package com.ecommerce.order.service.impl;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemRequestDto;
import com.ecommerce.order.dto.ProductResponseDto;
import com.ecommerce.order.dto.UserResponseDto;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import com.ecommerce.order.service.CartService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartRepository;

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient userServiceClient;

    private int attempts = 0;

    @CircuitBreaker(name="productService",fallbackMethod = "addToCartFallBack")
    @Retry(name="retryBreaker",fallbackMethod = "addToCartFallBack")
    @Override
    public boolean addToCart(String userId, CartItemRequestDto cartItemRequestDto) {
        System.out.println("Attempt number: " + (++attempts));
        ProductResponseDto productResponseDto =  productServiceClient.getProductDetails(cartItemRequestDto.getProductId());
        if(productResponseDto == null)
            return false;

        if(productResponseDto.getProductQuantity() <  cartItemRequestDto.getQuantity() )
            return false;

        UserResponseDto userOpt = userServiceClient.getUserDetails(userId);
        if(userOpt == null|| userOpt.getId() == null)
            return false;

        CartItem existingCartItem = cartRepository.findByUserIdAndProductId(userId, cartItemRequestDto.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequestDto.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequestDto.getProductId());
            cartItem.setQuantity(cartItemRequestDto.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(cartItem);
        }
        return true;
    }

    @Override
    public boolean addToCartFallBack(String userId, CartItemRequestDto cartItemRequestDto,Exception exception){
        System.out.println("Falling back to add to cart");
        return false;
    }

    @Override
    public boolean deleteItemFromCart(String userId, Long productId) {
        CartItem cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
            return true;
        }

        return false;
    }

    @Override
    public List<CartItem> getCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
