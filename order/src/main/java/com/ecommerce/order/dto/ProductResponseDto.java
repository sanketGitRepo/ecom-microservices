package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productCategory;
    private Integer productQuantity;
    private String productImageUrl;
    private Boolean isActive;
}
