package com.ecommerce.user.dto;

import com.ecommerce.user.enums.UserRole;
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
