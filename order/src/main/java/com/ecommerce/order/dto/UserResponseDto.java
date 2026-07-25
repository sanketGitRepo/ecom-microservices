package com.ecommerce.order.dto;

import com.ecommerce.order.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role = UserRole.CUSTOMER;
    private AddressDTo addressDTo;
}
