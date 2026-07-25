package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> searchProducts(String id);

    Optional<ProductResponseDto> updateProduct(ProductRequestDto productRequestDto, long id);

    Boolean deleteProduct(long id);

    Optional<ProductResponseDto> getProductById(long id);
}

