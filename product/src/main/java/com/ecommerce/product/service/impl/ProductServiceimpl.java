package com.ecommerce.product.service.impl;

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceimpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findByisActiveTrue().stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDto> searchProducts(String keyWord) {
        return productRepository.searchProducts(keyWord).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        updateProductFromRequest(product,productRequestDto);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    @Override
    public Optional<ProductResponseDto> updateProduct(ProductRequestDto productRequestDto, long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct,productRequestDto);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(updatedProduct);
                });
    }

    @Override
    public Boolean deleteProduct(long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setIsActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<ProductResponseDto> getProductById(long id) {
        return productRepository.findByIdAndIsActiveTrue(id)
                .map(this::mapToProductResponse);
    }

    private ProductResponseDto mapToProductResponse(Product savedProduct) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(savedProduct.getId());
        productResponseDto.setProductName(savedProduct.getProductName());
        productResponseDto.setIsActive(savedProduct.getIsActive());
        productResponseDto.setProductCategory(savedProduct.getProductCategory());
        productResponseDto.setProductDescription(savedProduct.getProductDescription());
        productResponseDto.setProductPrice(savedProduct.getProductPrice());
        productResponseDto.setProductImageUrl(savedProduct.getProductImageUrl());
        productResponseDto.setProductQuantity(savedProduct.getProductQuantity());
        return productResponseDto;
    }

    private void updateProductFromRequest(Product product, ProductRequestDto productRequestDto) {
        product.setProductName(productRequestDto.getProductName());
        product.setProductCategory(productRequestDto.getProductCategory());
        product.setProductDescription(productRequestDto.getProductDescription());
        product.setProductPrice(productRequestDto.getProductPrice());
        product.setProductImageUrl(productRequestDto.getProductImageUrl());
        product.setProductQuantity(productRequestDto.getProductQuantity());
    }
}
