package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderResponseDto;

import java.util.Optional;

public interface OrderService {
    Optional<OrderResponseDto> createOrder(String userId);
}
