package com.ecommerce.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productCategory;
    private Integer productQuantity;
    private String productImageUrl;
}
